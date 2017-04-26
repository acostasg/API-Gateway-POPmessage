package api.domain.command.request;

public class GetUserByTokenRequest extends ValidateTokenRequest {

    public GetUserByTokenRequest(String token) {
        super(token);
    }
}
