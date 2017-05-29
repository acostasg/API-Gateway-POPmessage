package api;


import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Before;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public abstract class AbstractTest extends ResourceConfig {

    static final String APP_KEY_TESTING = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
    static final String TOKEN_TESTING = "sakjfh97325437hskfsdfd_sdkjfsjf1283763339564921734sdfbdsj";

    private HttpServer server;
    WebTarget target;


    @Before
    public void setUp() throws Exception {
        // start the server
        this.server = Main.startServerTest();
        // create the client
        Client c = ClientBuilder.newClient();
        //set target for testing
        this.target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdownNow();
    }
}
