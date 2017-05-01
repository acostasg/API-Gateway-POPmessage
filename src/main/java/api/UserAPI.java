package api;

import api.domain.command.CommandLogin;
import api.domain.command.CommandRegisterUser;
import api.domain.command.request.LoginUserRequest;
import api.domain.command.request.RegisterUserRequest;
import api.domain.entity.Message;
import api.domain.entity.Token;
import api.domain.entity.User;
import api.domain.exceptions.InvalidAppKey;
import api.domain.service.ValidationAppService;
import api.infrastucture.inMemory.TokenRepository;
import api.infrastucture.inMemory.UserRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("user")
public class UserAPI extends AbstractAPI {


    @GET
    @Path("/login")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(
            @HeaderParam(value = "Authorization") String authorization,
            @QueryParam("userName") String userName,
            @QueryParam("password") String password
    ) throws InvalidAppKey {

        ValidationAppService.validateKeyApp(authorization);

        CommandLogin userCase = new CommandLogin(
                new UserRepository(),
                new TokenRepository()
        );

        Token token = userCase.execute(
                new LoginUserRequest(
                        userName,
                        password
                )
        );

        if (null == token || token.isEmpty())
            return Response.status(Response.Status.UNAUTHORIZED).entity(token).build();

        return Response.ok(token.toString(), MediaType.APPLICATION_JSON).build();
    }


    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String create(
            @HeaderParam(value = "Authorization") String authorization,
            @FormParam("name") String name,
            @FormParam("dateOfBirth") String dateOfBirth,
            @FormParam("userName") String userName,
            @FormParam("password") String password,
            @FormParam("privacyPolicy") Boolean privacyPolicy
    ) throws InvalidAppKey {

        ValidationAppService.validateKeyApp(authorization);

        CommandRegisterUser userCase = new CommandRegisterUser(
                new UserRepository()
        );

        User user = userCase.execute(
                new RegisterUserRequest(
                        name,
                        dateOfBirth,
                        userName,
                        password,
                        privacyPolicy
                )
        );

        return user.toString();
    }


    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/message/get")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMessages(
            @HeaderParam(value = "Authorization") String authorization,
            @QueryParam("Token") String token
    ) throws InvalidAppKey {

        ValidationAppService.validateKeyApp(authorization);

        User user = this.getUserByToken(token);

        List<Message> messageList = this.getMessagesByUser(user);

        return messageList.toString();
    }

}
