package api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UserTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        this.server = Main.startServer();
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
        server.stop();
    }


    @Test
    public void test_login() {
        String responseMsg = this.target.path("user/login")
                .queryParam("userName","name")
                .queryParam("password","1234")
                .request()
                .header("Authorization","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
                .get(String.class);
        assertEquals("Got it!", responseMsg);
    }

    @Test
    public void test_create() {
        Form input = new Form();
        input.param("name", "testName");
        input.param("dateOfBirth", "2014-02-13 02:42:48");
        input.param("userName", "testUserNanme");
        input.param("password", "testPassword");
        input.param("privacyPolicy", "testPrivacyPolicy");
        Entity<Form> entity = Entity.entity(input, MediaType.APPLICATION_FORM_URLENCODED);

        Response response = this.target.path("user/create")
                .request()
                .header("Authorization","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
                .post(entity);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void test_message_get() {

        String responseMsg = this.target.path("user/message/get")
                .request()
                .header("Authorization","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
                .get(String.class);
        assertEquals("Got it!", responseMsg);
    }
}
