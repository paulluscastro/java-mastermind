/**
 * 
 */
package br.com.paullus.mastermind.game.classes;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.paullus.mastermind.game.enums.GuessStatus;

/**
 * @author Paullus Martins de Sousa Nava Castro
 *
 */
public class Guess implements Serializable {
	
	private static final long serialVersionUID = 6576740886302907096L;
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	public String guess;
	public String clue;
	public String dateTime;
	public GuessStatus status;

	public Guess() {
		this.dateTime = FORMATTER.format(new Date());
	}
	
	public String getGuess() {
		return guess;
	}

	public void setGuess(String guess) {
		this.guess = guess;
	}
	
	public String getClue() {
		return clue;
	}
	
	public void setClue(String clue) {
		this.clue = clue;
	}
	
	public GuessStatus getStatus() {
		return status;
	}

	public void setStatus(GuessStatus status) {
		this.status = status;
	}
}
