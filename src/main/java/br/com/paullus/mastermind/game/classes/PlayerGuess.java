/**
 * 
 */
package br.com.paullus.mastermind.game.classes;

/**
 * @author Paullus Martins de Sousa Nava Castro
 *
 */
public class PlayerGuess extends Guess {
	
	private static final long serialVersionUID = -5070410879642648837L;
	
	String playerName;
	
	public PlayerGuess() {
		super();
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
}
