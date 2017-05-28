package api;


import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Before;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public abstract class AbstractTest extends ResourceConfig {

    static final String APP_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";

    static final String TOKEN = "sakjfh97325437hskfsdfd_sdkjfsjf1283763339564921734sdfbdsj";

    private HttpServer server;
    WebTarget target;


    @Before
    public void setUp() throws Exception {
        // start the server
        this.server = Main.startServerTest();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        this.target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdownNow();
    }
}
