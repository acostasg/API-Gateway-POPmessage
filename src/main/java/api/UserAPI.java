package api;

import api.domain.command.CommandLogin;
import api.domain.command.CommandLogout;
import api.domain.command.CommandRegisterUser;
import api.domain.command.request.LogOutUserRequest;
import api.domain.command.request.LoginUserRequest;
import api.domain.command.request.RegisterUserRequest;
import api.domain.entity.Message;
import api.domain.entity.Token;
import api.domain.entity.User;
import api.domain.exceptions.InvalidAppKey;
import api.domain.exceptions.UserInUse;
import api.domain.infrastructure.UserRepository;
import api.domain.service.ValidationAppService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("user")
public class UserAPI extends AbstractAPI {

    @Inject
    private UserRepository userRepository;

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
                this.userRepository,
                this.tokenRepository
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

    @GET
    @Path("/logout")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(
            @HeaderParam(value = "Authorization") String authorization,
            @QueryParam("Token") String token
    ) throws InvalidAppKey {

        ValidationAppService.validateKeyApp(authorization);

        User user = this.getUserByToken(token);

        if (null == user)
            return Response.status(Response.Status.BAD_REQUEST).build();

        CommandLogout userCase = new CommandLogout(
                this.tokenRepository
        );

        userCase.execute(
                new LogOutUserRequest(
                        user,
                        new Token(token)
                )
        );

        return Response.ok().build();
    }


    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(
            @HeaderParam(value = "Authorization") String authorization,
            @FormParam("name") String name,
            @FormParam("dateOfBirth") String dateOfBirth,
            @FormParam("userName") String userName,
            @FormParam("password") String password,
            @FormParam("privacyPolicy") Boolean privacyPolicy
    ) throws InvalidAppKey {

        ValidationAppService.validateKeyApp(authorization);

        CommandRegisterUser userCase = new CommandRegisterUser(
                this.userRepository
        );

        User user = null;
        try {
            user = userCase.execute(
                    new RegisterUserRequest(
                            name,
                            dateOfBirth,
                            userName,
                            password,
                            privacyPolicy
                    )
            );
        } catch (UserInUse userInUse) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        if (null == user)
            return Response.status(Response.Status.BAD_REQUEST).build();

        return Response.ok(user.toString(), MediaType.APPLICATION_JSON).build();
    }


    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/message/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessages(
            @HeaderParam(value = "Authorization") String authorization,
            @QueryParam("Token") String token
    ) throws InvalidAppKey {

        ValidationAppService.validateKeyApp(authorization);

        User user = this.getUserByToken(token);

        if (null == user)
            return Response.status(Response.Status.BAD_REQUEST).build();

        List<Message> messageList = this.getMessagesByUser(user);

        return Response.ok(messageList.toString(), MediaType.APPLICATION_JSON).build();
    }

}
