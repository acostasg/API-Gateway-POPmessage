package api;

import api.binder.POPMessageBinder;
import api.binder.POPMessageTestBinder;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 */
public class Main extends ResourceConfig {

    static final String BASE_URI = "http://0.0.0.0:8080/";
    private static final String CONFIG_LOG4J2_PATH = "/config/log4j2.properties";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     */
    private static void startServer() {
        BasicConfigurator.configure();
        final ResourceConfig rc = new ResourceConfig().packages("api");
        rc.register(new POPMessageBinder());
        GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * For testing propose with InMemoryRepositories
     *
     * @return Grizzly HTTP server.
     */
    static HttpServer startServerTest() {
        final ResourceConfig rc = new ResourceConfig().packages("api");
        rc.register(new POPMessageTestBinder());
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     *
     * @param args String[]
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        PropertyConfigurator.configure(CONFIG_LOG4J2_PATH);
        startServer();
        System.out.println(String.format("POPmessage app server started with WADL available at "
                + "%sapplication.wadl", BASE_URI));
    }
}

