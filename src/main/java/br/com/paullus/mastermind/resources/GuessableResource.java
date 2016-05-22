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

import br.com.paullus.mastermind.game.classes.Game;
import br.com.paullus.mastermind.game.classes.GamePool;
import br.com.paullus.mastermind.game.enums.GameStatus;

/**
 * @author Paullus Martins de Sousa Nava Castro
 *
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public abstract class GuessableResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("guess/{key}/{guess}")
	public Response guessAnswer(@PathParam("key") String key, @PathParam("guess") String guess) {
		Game game = GamePool.getInstance().getGame(key);
		if (game == null)
			return Response.noContent().build();
		else {
			if (game.getStatus() == GameStatus.OPEN)
				game.makeGuess(guess);
			else 
				return Response.ok().entity(game).build();
			
			if (game.getStatus() != GameStatus.OPEN) {
				return Response.ok().entity(game).build();
			} else {
				return Response.ok().entity(game.lastGuess()).build();
			}
		}
	}
}
