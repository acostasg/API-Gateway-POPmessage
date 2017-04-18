package api;

import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class MessageTest extends AbstractTest {

    @Test
    public void test_get() {
        String responseMsg = this.target.path("message/get")
                .queryParam("lat","12.123123")
                .queryParam("lon","34.234234")
                .request()
                .header("Authorization","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
                .get(String.class);
        assertEquals("Got it!", responseMsg);
    }

    @Test
    public void test_message_create() {
        Form input = new Form();
        input.param("text", "testName");
        input.param("lat", "34.234234");
        input.param("lon", "14.234234");
        Entity<Form> entity = Entity.entity(input, MediaType.APPLICATION_FORM_URLENCODED);

        Response response = this.target.path("message/create")
                .request()
                .header("Authorization","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
                .post(entity);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void test_delete() {
        Form input = new Form();
        input.param("message", "testMessage");
        Entity<Form> entity = Entity.entity(input, MediaType.APPLICATION_FORM_URLENCODED);

        Response response = this.target.path("message/delete")
                .request()
                .header("Authorization","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
                .post(entity);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}
