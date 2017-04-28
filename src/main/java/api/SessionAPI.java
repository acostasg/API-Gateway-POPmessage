package api;

import api.domain.command.CommandValidateToken;
import api.domain.command.request.ValidateTokenRequest;
import api.domain.entity.Token;
import api.domain.exceptions.InvalidAppKey;
import api.domain.service.ValidationAppService;
import api.infrastucture.inMemory.TokenRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("session")
public class SessionAPI {

    @GET
    @Path("/token")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String login(
            @HeaderParam(value = "Authorization") String authorization,
            @QueryParam("token") String token
    ) throws InvalidAppKey {

        ValidationAppService.validateKeyApp(authorization);

        CommandValidateToken userCase = new CommandValidateToken(
                new TokenRepository()
        );

        Token tokenValid = userCase.execute(
                new ValidateTokenRequest(
                        token
                )
        );

        return tokenValid.toString();
    }
}