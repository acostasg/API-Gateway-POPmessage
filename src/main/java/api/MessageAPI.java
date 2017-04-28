package api;

import api.domain.command.*;
import api.domain.command.request.*;
import api.domain.entity.Id;
import api.domain.entity.Message;
import api.domain.entity.Type;
import api.domain.entity.User;
import api.domain.exceptions.InvalidAppKey;
import api.domain.service.ValidationAppService;
import api.infrastucture.inMemory.MessageRepository;
import api.infrastucture.inMemory.TokenRepository;
import api.infrastucture.inMemory.UserRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("message")
public class MessageAPI {


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

        List<Message> messages = useCase.execute(
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
            @FormParam("lon") String lon,
            @QueryParam("token") String token
    ) throws InvalidAppKey {

        ValidationAppService.validateKeyApp(authorization);

        User user = getUserByToken(token);

        CommandCreateMessage useCaseCreateMessage = new CommandCreateMessage(
                new MessageRepository()
        );

        Message message = useCaseCreateMessage.execute(
                new CreateMessagesRequest(
                        text,
                        user,
                        lat,
                        lon
                )
        );

        return message.toString();
    }

    private User getUserByToken(@QueryParam("token") String token) {
        CommandGetUserByToken userCaseGetUserByToken = new CommandGetUserByToken(
                new UserRepository(),
                new CommandValidateToken(
                        new TokenRepository()
                )
        );

        return userCaseGetUserByToken.execute(
                new GetUserByTokenRequest(
                        token
                )
        );
    }

    @POST
    @Path("/vote/like/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String addVoteLike(
            @HeaderParam(value = "Authorization") String authorization,
            @FormParam("message") String messageId,
            @QueryParam("token") String token
    ) throws InvalidAppKey {

        ValidationAppService.validateKeyApp(authorization);

        Message message = this.getAddVoteToMessage(messageId, token, Type.POSITIVE);

        return message.toString();
    }

    @POST
    @Path("/vote/dislike/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String addVoteDislike(
            @HeaderParam(value = "Authorization") String authorization,
            @FormParam("message") String messageId,
            @QueryParam("token") String token
    ) throws InvalidAppKey {

        ValidationAppService.validateKeyApp(authorization);

        Message message = this.getAddVoteToMessage(messageId, token, Type.NEGATIVE);

        return message.toString();
    }

    private Message getAddVoteToMessage(@FormParam("message") String messageId, @QueryParam("token") String token, Type type) {
        User user = getUserByToken(token);

        CommandVoteMessage useCase = new CommandVoteMessage(
                new MessageRepository()
        );

        return useCase.execute(
                new VoteMessagesRequest(
                        user,
                        new Id(messageId),
                        type
                )
        );
    }


    @POST
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String delete(
            @HeaderParam(value = "Authorization") String authorization,
            @FormParam("message") String messageId,
            @QueryParam("token") String token
    ) throws InvalidAppKey {

        ValidationAppService.validateKeyApp(authorization);

        User user = getUserByToken(token);

        CommandDeleteMessage useCase = new CommandDeleteMessage(
                new MessageRepository()
        );

        Message message = useCase.execute(
                new DeleteMessagesRequest(
                        user,
                        new Id(messageId)
                )
        );

        return message.toString();
    }

}
