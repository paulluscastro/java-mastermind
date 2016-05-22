/**
 * 
 */
package br.com.paullus.mastermind.game.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * @author Paullus Martins de Sousa Nava Castro
 * This class holds the game pool of the API. Every game is registered here after creation.
 */
public class GamePool {

	private Map<String, Game> pool;
	private static GamePool instance;
	private final static Logger logger = Logger.getLogger(GamePool.class);
	
	private GamePool() {
		logger.info("Creating game pool");
		pool = new HashMap<String, Game>();
	}
	
	/***
	 * Get the instance of the GamePool
	 * @return
	 */
	public static GamePool getInstance() {
		if (instance == null)
			instance = new GamePool();
		return instance;
	}
	
	/***
	 * Adds a game to the pool
	 * @param game Game to be added
	 */
	public void addGame(Game game) {
		logger.debug("Game " + game.getKey() + " added to pool.");
		pool.put(game.getKey(), game);
	}
	
	/***
	 * Retrieve a game searching by it's key 
	 * @param key key of the game to be retrieved
	 * @return If the game exists returns it, otherwise returns 
	 */
	public Game getGame(String key) {
		Game game = pool.get(key.toUpperCase());
		
		if (game == null)
			logger.error("Game '" + key + "' doesn't exist in the pool.");
		
		return game;
	}
	
	/***
	 * Clear ALL games in the pool, including the valid ones
	 */
	public void clearGames() {
		logger.info("The party is over. Go home for your wives, boys...");
		pool.clear();
	}
	
	/***
	 * Remove expired games from the pool, including the valid ones
	 */
	public void removeExpired() {
		logger.info("Removing expired games...");
		List<Game> removable = new ArrayList<Game>();
		
		Set<Entry<String, Game>> entries = pool.entrySet();
		Iterator<Entry<String, Game>> iterator = entries.iterator();
		while (iterator.hasNext()) {
			Entry<String, Game> entry = iterator.next();
			if (entry.getValue().isExpired())
				removable.add(entry.getValue());
		}
		
		for (Game game: removable)
			pool.remove(game.getKey());
		
		logger.info("Removed " + removable.size() + "." + (removable.size() > 0 ? "Heap memory breaths in relief." : ""));
	}
}
