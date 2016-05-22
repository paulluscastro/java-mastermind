/**
 * 
 */
package br.com.paullus.mastermind.game.classes;

/**
 * @author Paullus Martins de Sousa Nava Castro
 *
 */
public class ProposedGameRules extends GameRules {
	
	private static final long serialVersionUID = -8154234970570292506L;
	
	private String password;

	public ProposedGameRules() {
		super();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
