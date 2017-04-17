package api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("message")
public class Message {


    @GET
    @Path("/get")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String get(@QueryParam("lat") String lat, @QueryParam("lon") String lon) {
        return "Got it!";
    }


    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String get(@FormParam("text") String text, @FormParam("lat") String lat, @FormParam("lon") String lon) {
        return "Got it!";
    }


    @POST
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String get(@FormParam("message") String message) {
        return "Got it!";
    }

}
