package br.com.paullus.mastermind.game.classes;

import java.io.Serializable;

/**
 * @author Paullus Martins de Sousa Nava Castro
 * 
 */
public class GameRules implements Serializable{
	
	private static final long serialVersionUID = -1272887968177372654L;
	
	private String key;
	private String allowed = "ABCDEFGH";
	private int minutesAlive = 5;
	private boolean allowRepeats = false;
	private int maximumGuesses = 0;
	private int passwordSize = 8;

	public GameRules() {
	}

	public GameRules(String key) {
		this.key = key;
	}
	
	public GameRules(String key, GameRules rules) {
		this(key);

		if (rules.allowed != null)
			this.allowed = rules.allowed;
		if (rules.getMinutesAlive() > 0)
			this.minutesAlive = rules.minutesAlive;
		if (rules.passwordSize > 0)
			this.passwordSize = rules.passwordSize;

		this.allowRepeats = rules.allowRepeats;
		this.maximumGuesses= rules.maximumGuesses;
	}

	public String getKey() {
		return key;
	}

	public String getAllowed() {
		return allowed;
	}
	
	public int getMinutesAlive() {
		return minutesAlive;
	}
	
	public boolean isRepeatAllowed() {
		return allowRepeats;
	}
	
	public int getMaximumGuesses() {
		return maximumGuesses;
	}
	
	public int getPasswordSize() {
		return passwordSize;
	}

	public void setAllowRepeats(boolean allowRepeats) {
		this.allowRepeats = allowRepeats;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setAllowed(String allowed) {
		this.allowed = allowed;
	}

	public void setMinutesAlive(int minutesAlive) {
		this.minutesAlive = minutesAlive;
	}

	public void setMaximumGuesses(int maximumGuesses) {
		this.maximumGuesses = maximumGuesses;
	}

	public void setPasswordSize(int passwordSize) {
		this.passwordSize = passwordSize;
	}
}