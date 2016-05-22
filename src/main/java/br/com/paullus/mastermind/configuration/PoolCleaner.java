/**
 * 
 */
package br.com.paullus.mastermind.configuration;

import java.util.TimerTask;

import br.com.paullus.mastermind.game.classes.GamePool;

/**
 * @author Paullus Martins de Sousa Nava Castro
 *
 */
public class PoolCleaner extends TimerTask {

	/* (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		GamePool.getInstance().removeExpired();
	}

}
