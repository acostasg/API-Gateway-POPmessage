package api.domain.command.request;

import api.domain.entity.Id;
import api.domain.entity.User;

public class DeleteMessagesRequest implements Request {

    private User user;
    private Id messageId;

    public DeleteMessagesRequest(
            User user,
            Id message
    ) {
        this.user = user;
        this.messageId = message;
    }

    public User User() {
        return user;
    }

    public Id MessageId() {
        return messageId;
    }
}
