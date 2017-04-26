package api;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UserTest extends AbstractTest {

    @Test
    public void test_login() {
        String responseMsg = this.target.path("user/login")
                .queryParam("userName","name")
                .queryParam("password","1234")
                .request()
                .header("Authorization","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
                .get(String.class);
        assertEquals("{\"Token\":{\"hash\":\"sakjfh97325437hskfsdfd_sdkjfsjf1283763339564921734sdfbdsj\"}}", responseMsg);
    }

    @Test
    public void test_user_create() {
        Form input = new Form();
        input.param("name", "testName");
        input.param("dateOfBirth", "2014-02-13 02:42:48");
        input.param("userName", "testUserNanme");
        input.param("password", "testPassword");
        input.param("privacyPolicy", "true");
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
