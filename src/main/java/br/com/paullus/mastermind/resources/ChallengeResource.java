/**
 * 
 */
package br.com.paullus.mastermind.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import br.com.paullus.mastermind.game.classes.Game;
import br.com.paullus.mastermind.game.classes.GamePool;
import br.com.paullus.mastermind.game.classes.ProposedGameRules;

/**
 * @author Paullus Martins de Sousa Nava Castro
 *
 */
@Path("challenge")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ChallengeResource extends GuessableResource {

	private static final Logger logger = Logger.getLogger(ChallengeResource.class);
	
	/***
	 * 
	 * @param rules
	 * @param password
	 * @return
	 */
	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createGame(ProposedGameRules rules) {
		try {
			Game game = new Game(rules.getPassword(), rules);
			
			GamePool.getInstance().addGame(game);
			logger.debug("Game " + game.getKey() + " password is '" + game.getPassword() + "'");
			return Response.ok(game.getRules()).build();
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).type(MediaType.APPLICATION_JSON).build();
		}
	}
}
