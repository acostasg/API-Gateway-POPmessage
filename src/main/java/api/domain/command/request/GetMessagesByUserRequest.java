package api.domain.command.request;

import api.domain.entity.User;

public class GetMessagesByUserRequest implements Request {

    private User user;

    public GetMessagesByUserRequest(
            User user
    ) {
        this.user = user;
    }

    public User user() {
        return user;
    }
}
