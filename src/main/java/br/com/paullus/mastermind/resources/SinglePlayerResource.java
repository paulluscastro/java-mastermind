/**
 * 
 */
package br.com.paullus.mastermind.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import br.com.paullus.mastermind.game.classes.Game;
import br.com.paullus.mastermind.game.classes.GamePool;

/**
 * @author Paullus Martins de Sousa Nava Castro
 *
 */
@Path("single")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SinglePlayerResource extends GuessableResource {

	private static final Logger logger = Logger.getLogger(SinglePlayerResource.class);
	
	@GET
	@Path("create")
	public Response createGame() {
		Game game = new Game();
		GamePool.getInstance().addGame(game);
		logger.debug("Game " + game.getKey() + " password is '" + game.getPassword() + "'");
		return Response.ok(game.getRules()).build();
	}
}
