package api.domain.command.request;

import api.domain.entity.User;

public class GetMessagesByUserRequest implements Request {

    private User user;
    private int last;

    public GetMessagesByUserRequest(
            User user,
            int last
    ) {
        this.user = user;
        this.last = last;
    }

    public User user() {
        return user;
    }

    public int last() { return last; }
}
