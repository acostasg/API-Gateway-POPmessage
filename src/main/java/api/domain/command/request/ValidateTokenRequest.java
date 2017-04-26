package api.domain.command.request;

import api.domain.entity.Token;

public class ValidateTokenRequest implements Request {

    private Token token;

    public ValidateTokenRequest(
            String token
    ){
        this.token = new Token(token);
    }

    public Token token() {
        return this.token;
    }

}
