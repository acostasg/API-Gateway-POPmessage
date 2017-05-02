package api;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.json.stream.JsonParser;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class UserTest extends AbstractTest {

    @Test
    public void test_login() {
        String responseMsg = this.target.path("user/login")
                .queryParam("userName","name@domain.com")
                .queryParam("password","1234567")
                .request()
                .header("Authorization",APP_KEY)
                .get(String.class);
        assertEquals("{\"Token\":{\"hash\":\"sakjfh97325437hskfsdfd_sdkjfsjf1283763339564921734sdfbdsj\"}}", responseMsg);
    }

    @Test
    public void test_logout() {
        Response responseMsg = this.target.path("user/logout")
                .queryParam("Token",TOKEN)
                .request()
                .header("Authorization",APP_KEY)
                .get();
        assertEquals(Response.Status.OK.getStatusCode(), responseMsg.getStatus());
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
                .header("Authorization",APP_KEY)
                .post(entity);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void test_message_get() throws ParseException {

        String responseMsg = this.target.path("user/message/get")
                .queryParam("Token",TOKEN)
                .request()
                .header("Authorization",APP_KEY)
                .get(String.class);

        JSONParser parser = new JSONParser();

        Object obj = parser.parse(responseMsg);
        JSONArray jsonArray = (JSONArray) obj;

        assertEquals(10,  jsonArray.size());
    }
}
