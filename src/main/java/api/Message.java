package api;

import api.domain.command.CommandGetMessagesByLocation;
import api.domain.command.request.GetMessagesByLocationRequest;
import api.domain.exceptions.InvalidAppKey;
import api.domain.service.ValidationAppService;
import api.infrastucture.inMemory.MessageRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("message")
public class Message {


    @GET
    @Path("/get")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String get(
            @HeaderParam(value = "Authorization") String authorization,
            @QueryParam("lat") String lat,
            @QueryParam("lon") String lon
    ) throws InvalidAppKey {

        ValidationAppService.validateKeyApp(authorization);

        CommandGetMessagesByLocation useCase = new CommandGetMessagesByLocation(
                new MessageRepository()
        );

        List<api.domain.entity.Message> messages = useCase.execute(
                new GetMessagesByLocationRequest(
                        lat,
                        lon
                )
        );

        return messages.toString();
    }


    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String create(
            @HeaderParam(value = "Authorization") String authorization,
            @FormParam("text") String text,
            @FormParam("lat") String lat,
            @FormParam("lon") String lon
    ) throws InvalidAppKey {

        ValidationAppService.validateKeyApp(authorization);

        return "Got it!";
    }


    @POST
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String delete(
            @HeaderParam(value = "Authorization") String authorization,
            @FormParam("message") String message
    ) throws InvalidAppKey {

        ValidationAppService.validateKeyApp(authorization);

        return "Got it!";
    }

}
