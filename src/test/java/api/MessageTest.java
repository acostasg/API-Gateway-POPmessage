package api;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class MessageTest extends AbstractTest {

    @Test
    public void test_get() throws ParseException {
        String responseMsg = this.target.path("message/get")
                .queryParam("lat", "12.123123")
                .queryParam("lon", "34.234234")
                .queryParam("Token", TOKEN_TESTING)
                .request()
                .header("Authorization", APP_KEY_TESTING)
                .get(String.class);

        JSONParser parser = new JSONParser();

        Object obj = parser.parse(responseMsg);
        JSONArray jsonArray = (JSONArray) obj;

        assertEquals(10, jsonArray.size());
    }

    @Test
    public void test_message_create() {
        Form input = new Form();
        input.param("text", "testName");
        input.param("lat", "34.234234");
        input.param("lon", "14.234234");
        Entity<Form> entity = Entity.entity(input, MediaType.APPLICATION_FORM_URLENCODED);

        Response response = this.target.path("message/create")
                .queryParam("Token", TOKEN_TESTING)
                .request()
                .header("Authorization", APP_KEY_TESTING)
                .post(entity);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void test_message_vote_like() {
        Form input = new Form();
        input.param("message", "DummyId");
        Entity<Form> entity = Entity.entity(input, MediaType.APPLICATION_FORM_URLENCODED);

        Response response = this.target.path("message/vote/like/create")
                .queryParam("Token", TOKEN_TESTING)
                .request()
                .header("Authorization", APP_KEY_TESTING)
                .post(entity);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void test_message_vote_dislike() {
        Form input = new Form();
        input.param("message", "DummyId");
        Entity<Form> entity = Entity.entity(input, MediaType.APPLICATION_FORM_URLENCODED);

        Response response = this.target.path("message/vote/dislike/create")
                .queryParam("Token", TOKEN_TESTING)
                .request()
                .header("Authorization", APP_KEY_TESTING)
                .post(entity);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void test_message_update() {
        Form input = new Form();
        input.param("message", "DummyId");
        input.param("text", "Text Sample update");
        Entity<Form> entity = Entity.entity(input, MediaType.APPLICATION_FORM_URLENCODED);

        Response response = this.target.path("message/update")
                .queryParam("Token", TOKEN_TESTING)
                .request()
                .header("Authorization", APP_KEY_TESTING)
                .post(entity);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void test_delete() {
        Form input = new Form();
        input.param("message", "DummyId");
        Entity<Form> entity = Entity.entity(input, MediaType.APPLICATION_FORM_URLENCODED);

        Response response = this.target.path("message/delete")
                .queryParam("Token", TOKEN_TESTING)
                .request()
                .header("Authorization", APP_KEY_TESTING)
                .post(entity);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}
