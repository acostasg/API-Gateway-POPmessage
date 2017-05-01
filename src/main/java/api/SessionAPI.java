package api;

import api.domain.command.CommandValidateToken;
import api.domain.command.request.ValidateTokenRequest;
import api.domain.entity.Token;
import api.domain.exceptions.InvalidAppKey;
import api.domain.service.ValidationAppService;
import api.infrastucture.inMemory.TokenRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("session")
public class SessionAPI {

    @GET
    @Path("/token")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(
            @HeaderParam(value = "Authorization") String authorization,
            @QueryParam("Token") String token
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

        if (null == tokenValid || tokenValid.isEmpty())
            return Response.status(Response.Status.BAD_REQUEST).build();

        return Response.ok(tokenValid.toString(), MediaType.APPLICATION_JSON).build();
    }
}