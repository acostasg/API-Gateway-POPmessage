package api;

import api.domain.command.CommandCreateMessage;
import api.domain.command.CommandDeleteMessage;
import api.domain.command.CommandGetMessagesByLocation;
import api.domain.command.CommandUpdateMessage;
import api.domain.command.request.CreateMessagesRequest;
import api.domain.command.request.DeleteMessagesRequest;
import api.domain.command.request.GetMessagesByLocationRequest;
import api.domain.command.request.UpdateMessagesRequest;
import api.domain.entity.Id;
import api.domain.entity.Message;
import api.domain.entity.Type;
import api.domain.entity.User;
import api.domain.exceptions.InvalidAppKey;
import api.domain.exceptions.InvalidUser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("message")
public class MessageAPI extends AbstractAPI {


    @GET
    @Path("/get")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(
            @HeaderParam(value = "Authorization") String authorization,
            @QueryParam("lat") String lat,
            @QueryParam("lon") String lon,
            @QueryParam("Token") String token
    ) throws InvalidAppKey {

        this.validationAppService.validationKey(authorization);

        User user = this.getUserByToken(token);

        if (null == user)
            return Response.status(Response.Status.UNAUTHORIZED).build();

        CommandGetMessagesByLocation useCase = new CommandGetMessagesByLocation(
                this.messageRepository
        );

        List<Message> messages = useCase.execute(
                new GetMessagesByLocationRequest(
                        lat,
                        lon
                )
        );


        return Response.ok(messages.toString(), MediaType.APPLICATION_JSON).build();
    }


    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(
            @HeaderParam(value = "Authorization") String authorization,
            @FormParam("text") String text,
            @FormParam("lat") String lat,
            @FormParam("lon") String lon,
            @QueryParam("Token") String token
    ) throws InvalidAppKey {

        this.validationAppService.validationKey(authorization);

        User user = this.getUserByToken(token);

        if (null == user)
            return Response.status(Response.Status.UNAUTHORIZED).build();

        CommandCreateMessage userCase = new CommandCreateMessage(
                this.messageRepository
        );

        Message message = userCase.execute(
                new CreateMessagesRequest(
                        text,
                        user,
                        lat,
                        lon
                )
        );

        return Response.ok(message.toString(), MediaType.APPLICATION_JSON).build();
    }


    @POST
    @Path("/vote/like/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addVoteLike(
            @HeaderParam(value = "Authorization") String authorization,
            @FormParam("message") String messageId,
            @QueryParam("Token") String token
    ) throws InvalidAppKey {

        this.validationAppService.validationKey(authorization);

        try {
            Message message = this.getAddVoteToMessage(messageId, token, Type.POSITIVE);
            return Response.ok(message.toString(), MediaType.APPLICATION_JSON).build();
        } catch (InvalidUser exception) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("/vote/dislike/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addVoteDislike(
            @HeaderParam(value = "Authorization") String authorization,
            @FormParam("message") String messageId,
            @QueryParam("Token") String token
    ) throws InvalidAppKey {

        this.validationAppService.validationKey(authorization);

        try {
            Message message = this.getAddVoteToMessage(messageId, token, Type.NEGATIVE);
            return Response.ok(message.toString(), MediaType.APPLICATION_JSON).build();
        } catch (InvalidUser exception) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }


    @POST
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(
            @HeaderParam(value = "Authorization") String authorization,
            @FormParam("message") String messageId,
            @QueryParam("Token") String token
    ) throws InvalidAppKey {

        this.validationAppService.validationKey(authorization);

        User user = this.getUserByToken(token);

        if (null == user)
            return Response.status(Response.Status.BAD_REQUEST).build();

        CommandDeleteMessage useCase = new CommandDeleteMessage(
                this.messageRepository
        );

        Message message = useCase.execute(
                new DeleteMessagesRequest(
                        user,
                        new Id(messageId)
                )
        );

        return Response.ok(message.toString(), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response put(
            @HeaderParam(value = "Authorization") String authorization,
            @FormParam("text") String text,
            @FormParam("message") String messageId,
            @QueryParam("Token") String token
    ) throws InvalidAppKey {

        this.validationAppService.validationKey(authorization);

        User user = this.getUserByToken(token);

        if (null == user)
            return Response.status(Response.Status.BAD_REQUEST).build();


        CommandUpdateMessage useCase = new CommandUpdateMessage(
                this.messageRepository
        );

        Message message = useCase.execute(
                new UpdateMessagesRequest(
                        new Id(messageId),
                        text,
                        user
                )
        );

        if (null == message)
            return Response.status(Response.Status.UNAUTHORIZED).build();


        return Response.ok(user.toString(), MediaType.APPLICATION_JSON).build();
    }

}
