package br.com.paullus.mastermind;

import java.io.IOException;
import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import br.com.paullus.mastermind.configuration.CustomMapperProvider;
import br.com.paullus.mastermind.configuration.PoolCleaner;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/mastermind/api/";
	private final static int MINUTES = 1;

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in br.com.paullus.mastermind package
        final ResourceConfig rc = new ResourceConfig()
        		.packages("br.com.paullus.mastermind")
        		.register(CustomMapperProvider.class)
        		.register(JacksonFeature.class);

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
    	// Timer created according to example available at:
    	// https://examples.javacodegeeks.com/core-java/util/timertask/java-timer-and-timertask-example-tutorial/
    	TimerTask timerTask = new PoolCleaner();
    	Timer timer = new Timer(true);
    	timer.scheduleAtFixedRate (timerTask, MINUTES * 60 * 1000, MINUTES * 60 * 1000);
    	
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}

