package br.com.paullus.mastermind.game.classes;

import java.io.Serializable;
import java.util.List;

import br.com.paullus.mastermind.game.enums.GameStatus;

/**
 * @author Paullus Martins de Sousa Nava Castro
 *
 */
public class MultiplayerGameInfo implements Serializable{
	
	private static final long serialVersionUID = 7234918093004530671L;
	
	private String key;
	private GameRules rules;
	private List<Guess> guesses;
	private GameStatus status;
	private List<String> registeredPlayers;
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public GameRules getRules() {
		return rules;
	}
	
	public void setRules(GameRules rules) {
		this.rules = rules;
	}
	
	public List<Guess> getGuesses() {
		return guesses;
	}
	
	public void setGuesses(List<Guess> guesses) {
		this.guesses = guesses;
	}
	
	public GameStatus getStatus() {
		return status;
	}
	
	public void setStatus(GameStatus status) {
		this.status = status;
	}
	
	public List<String> getRegisteredPlayers() {
		return registeredPlayers;
	}
	
	public void setRegisteredPlayers(List<String> registeredPlayers) {
		this.registeredPlayers = registeredPlayers;
	}
}
