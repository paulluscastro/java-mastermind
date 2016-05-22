/**
 * 
 */
package br.com.paullus.mastermind.game.classes;

import java.util.ArrayList;
import java.util.List;

import br.com.paullus.mastermind.game.enums.GameStatus;
import br.com.paullus.mastermind.game.enums.GuessStatus;

/**
 * @author Paullus Martins de Sousa Nava Castro
 *
 * This is a specialized type of game, which controls the number of players currently playing this game.
 */
public class MultiplayerGame extends Game {
	
	private static final long serialVersionUID = 4399499722111293586L;
	
	private List<String> registeredPlayers = new ArrayList<String>();
	private String winner = "";

	public MultiplayerGame() {
		super();
		this.status = GameStatus.WAIT;
	}
	
	public MultiplayerGame(String password) {
		super(password);
		this.status = GameStatus.WAIT;
	}
	
	public MultiplayerGame(GameRules rules) {
		super(rules);
		this.status = GameStatus.WAIT;
	}
	
	public MultiplayerGame(String password, GameRules rules) {
		super(password, rules);
		this.status = GameStatus.WAIT;
	}
	
	public String getWinner() {
		return winner;
	}
	
	public List<String> getRegisteredPlayers() {
		return registeredPlayers;
	}
	
	public void addUser(String userName) {
		if (!registeredPlayers.contains(userName.trim().toUpperCase())) {
			registeredPlayers.add(userName.trim().toUpperCase());
			this.status = registeredPlayers.size() < 2 ? GameStatus.WAIT : GameStatus.OPEN;
		}
	}
	
	public MultiplayerGameInfo generateGameInfo() {
		MultiplayerGameInfo info = new MultiplayerGameInfo();
		info.setRegisteredPlayers(registeredPlayers);
		info.setGuesses(getGuesses());
		info.setKey(getKey());
		info.setRules(getRules());
		info.setStatus(getStatus());
		
		return info;
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
	 * @param player name of the player who is guessing  
	 * @param password guessed password
	 * @return return the clue of possible matches in the guessed password
	 */
	public String makeGuess(String player, String password){
		if (status != GameStatus.OPEN)
			return "GAME NOT OPEN.";
			
		
		player = player.toUpperCase();
		password = password.toUpperCase();
		
		PlayerGuess guess = new PlayerGuess();
		guess.setPlayerName(player);
		guess.setGuess(password);
		if (!registeredPlayers.contains(player.trim().toUpperCase())) {
			guess.setStatus(GuessStatus.INVALID_PLAYER);

			StringBuilder sb = new StringBuilder(guess.getGuess().length()); 
			for(int i = 0; i < guess.getGuess().length(); i++)
				sb.append("X");
			
			guess.setClue(sb.toString());
			addGuess(guess);
			
			return guess.getClue();
		}
		String result = makeGuess(guess); 
		if (status == GameStatus.BEATEN)
			winner = player;
		
		return result;
	}
	
	@Override
	protected void addGuess(Guess guess) {
		getGuesses().add(0, guess);
	}
}
