package api.domain.command.request;

import api.domain.entity.Token;
import api.domain.entity.User;

public class LogOutUserRequest implements Request {

    private User user;
    private Token token;

    public LogOutUserRequest(
            User user,
            Token token
    ) {
        this.user = user;
        this.token = token;
    }

    public User User() {
        return user;
    }

    public Token Token() {
        return token;
    }

}
