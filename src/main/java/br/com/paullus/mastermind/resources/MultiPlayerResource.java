/**
 * 
 */
package br.com.paullus.mastermind.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import br.com.paullus.mastermind.game.classes.Game;
import br.com.paullus.mastermind.game.classes.GamePool;
import br.com.paullus.mastermind.game.classes.MultiplayerGame;
import br.com.paullus.mastermind.game.enums.GameStatus;

/**
 * @author Paullus Martins de Sousa Nava Castro
 *
 */
@Path("multi")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MultiPlayerResource {

	private static final Logger logger = Logger.getLogger(MultiPlayerResource.class);
	
	@GET
	@Path("create/{user}")
	public Response createGame(@PathParam("user") String user) {
		MultiplayerGame game = new MultiplayerGame();
		game.addUser(user);
		
		GamePool.getInstance().addGame(game);
		logger.debug("Game " + game.getKey() + " password is '" + game.getPassword() + "'");
		return Response.ok(game.generateGameInfo()).build();
	}
	
	@GET
	@Path("enter/{key}/{user}")
	public Response addUser(@PathParam("key") String key, @PathParam("user") String user) {
		Game game = GamePool.getInstance().getGame(key);
		
		if ((game == null) || !(game instanceof MultiplayerGame))
			return Response.noContent().build();
		else {
			MultiplayerGame multi = (MultiplayerGame)game;
			
			multi.addUser(user);
			return Response.ok(multi.generateGameInfo()).build();
		}
	}
	@GET
	@Path("status/{key}")
	@Produces(MediaType.TEXT_PLAIN)
	public String addUser(@PathParam("key") String key) {
		Game game = GamePool.getInstance().getGame(key);
		
		if ((game == null) || !(game instanceof MultiplayerGame))
			return "Multiplayer game not found";
		else {
			MultiplayerGame multi = (MultiplayerGame)game;
			
			return multi.getStatus().toString();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("guess/{key}/{user}/{guess}")
	public Response guessAnswer(@PathParam("key") String key, @PathParam("user") String user, @PathParam("guess") String guess) {
		Game game = GamePool.getInstance().getGame(key);
		
		if ((game == null) || !(game instanceof MultiplayerGame))
			return Response.noContent().build();
		else {
			MultiplayerGame multi = (MultiplayerGame)game;
			
			if (multi.getStatus() == GameStatus.OPEN)
				multi.makeGuess(user, guess);
			else 
				return Response.ok().entity(multi.generateGameInfo()).build();
			
			if (multi.getStatus() != GameStatus.OPEN) {
				return Response.ok().entity(multi).build();
			} else {
				return Response.ok().entity(multi.getGuesses()).build();
			}
		}
	}
}
