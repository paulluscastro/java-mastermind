/**
 * 
 */
package br.com.paullus.mastermind.game.classes;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import br.com.paullus.mastermind.game.enums.GameStatus;
import br.com.paullus.mastermind.game.enums.GuessStatus;

/**
 * @author Paullus Martins de Sousa Nava Castro
 *
 */
public class Game implements Serializable {
	
	private static final long serialVersionUID = -3521776726339072919L;
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	private Date created;
	private Date closed;
	private String key;
	private String password;
	protected GameRules rules;
	private List<Guess> guesses;
	protected GameStatus status = GameStatus.OPEN;
	private static final Logger logger = Logger.getLogger(Game.class);
	
	public Game() {
		this.created = new Date();
		this.key = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
		this.rules = new GameRules(key);
		this.guesses = new ArrayList<Guess>();
		this.password = createRandomPassword();
	}
	
	public Game(String password) {
		this();
		this.password = password;
	}
	
	public Game(GameRules rules) {
		this();
		this.rules = new GameRules(key, rules);
	}
	
	public Game(String password, GameRules rules) {
		this();
		if (rules != null)
			this.rules = new GameRules(key, rules);
		
		if ((password != null) && (!password.isEmpty()))
			this.password = password;
		else
			// Create a new password base in the provided rules
			this.password = createRandomPassword();
		
		if ((rules != null) && (password != null) && (!password.isEmpty()))
			passwordConforming(password, rules);
	}
	
	public String getCreated() {
		return FORMATTER.format(created);
	}
	
	public String getClosed() {
		return closed != null ? FORMATTER.format(closed) : "";
	}

	public String getKey() {
		return key;
	}

	public String getPassword() {
		return password;
	}

	public GameRules getRules() {
		return rules;
	}
	
	public boolean isExpired(){
		long diff = new Date().getTime() - created.getTime();
		return diff > (rules.getMinutesAlive() * 60 * 1000);
	}
	
	private void passwordConforming(String password, GameRules rules){
		if (password.length() != rules.getPasswordSize())
			logger.error("Password size doesn't conform to provided rules...");
		
		for (int i = 0; i < password.length(); i++)
			if (rules.getAllowed().indexOf(String.valueOf(password.charAt(i)).toUpperCase()) == -1) {
				logger.error("Password have options that conform to provided rules...");
				return;
			}
		
	}
	
	/***
	 * Creates a random password to this game
	 * @return the generated random password
	 */
	private String createRandomPassword(){
		StringBuilder sb = new StringBuilder(rules.getPasswordSize());
		StringBuilder allowed = new StringBuilder(rules.getAllowed());
		while(sb.length() < rules.getPasswordSize()) {
			int position = Double.valueOf(Math.random() * allowed.length()).intValue();
			sb.append(String.valueOf(allowed.charAt(position)).toUpperCase());
			
			if (!rules.isRepeatAllowed())
				allowed.deleteCharAt(position);
		}
		
		return sb.toString();
	}

	private String createGuessResult(String password) {
		
		StringBuilder answer = new StringBuilder();
		
		if (password.length() != this.password.length()) {
			int diff = Math.abs(this.password.length() - password.length());
			for (int i = 0; i < diff; i++)
				answer.append('X');
			
			if (password.length() > this.password.length())
				password = password.substring(0, this.password.length());
		}
		
		// Removing perfect matches
		StringBuilder original = new StringBuilder(this.password);
		StringBuilder guessed = new StringBuilder(password);
		
		for(int i = original.length() - 1; i >= 0; i--)
			if (original.charAt(i) - guessed.charAt(i) == 0) {
				answer.append('1');
				guessed.deleteCharAt(i);
				original.deleteCharAt(i);
			}
		
		// Removing "mispositioned matches"
		for(int i = guessed.length() - 1; i >= 0; i--)
			 if (original.indexOf(String.valueOf(guessed.charAt(i))) >= 0) {
					answer.append('9');
					original.deleteCharAt(original.indexOf(String.valueOf(guessed.charAt(i))));
					guessed.deleteCharAt(i);
				}
		
		// Adding mispositioned matches
		for(int i = 0; i < guessed.length(); i++)
			answer.append('X');
		
		// Cosmetic stuff (putting in order: matches, mispositioned matches & unmatches)
		char[] sortable = new char[answer.length()];
		answer.getChars(0, answer.length(), sortable, 0);
		Arrays.sort(sortable);
		return new String(sortable);
	}
	
	protected String makeGuess(Guess guess){
		guess.setClue(createGuessResult(guess.getGuess()));
		if (guess.getClue().replaceAll("1", "").equals("")) {
			guess.setStatus(GuessStatus.CORRECT);
		} else {
			guess.setStatus(GuessStatus.WRONG);
		}
		
		
		addGuess(guess);
		
		if (guess.getClue().replaceAll("1", "").equals("") || guesses.size() == rules.getMaximumGuesses()) {
			this.status = guesses.size() == rules.getMaximumGuesses() ? GameStatus.LOST : GameStatus.BEATEN;
			
			this.closed = new Date();
			long time = (closed.getTime() - created.getTime()) / 1000;
			if (this.status == GameStatus.LOST)
				logger.info("You lose! Game " + key + " has reached maximum allowed guesses. Time: " + time + " seconds / Tries: " + guesses.size());
			else
				logger.info("We got a winner! Game " + key + " was beaten. Time: " + time + " seconds / Tries: " + guesses.size());
		}
		
		return guess.getClue();
	}
	
	/***
	 * Compares the guessed password with this Game's password.
	 * This method will return a String with zeros, ones and nines, where:
	 * 
	 * - 0 represents a letter that didn't match any other letter in the game password
	 * - 1 represents a letter that perfectly matched it correspondent letter and position in the game password
	 * - 9 represents a letter that matches a correspondent letter in the game password but it's not in the correct position;
	 * 
	 * If passwords have different sizes it will return the biggest of both, filling empty spaces with 0.
	 * @param password
	 * @return
	 */
	public String makeGuess(String password){
		if (status != GameStatus.OPEN)
			return "GAME NOT OPEN.";
		
		password = password.toUpperCase();
		
		Guess guess = new Guess();
		guess.setGuess(password);
		
		return makeGuess(guess);
	}

	public GameStatus getStatus() {
		return status;
	}
	
	public List<Guess> getGuesses() {
		return guesses;
	}
	
	public Guess lastGuess() {
		return guesses.get(guesses.size() - 1);
	}
	
	protected void addGuess(Guess guess) {
		guesses.add(guess);
	}
}
