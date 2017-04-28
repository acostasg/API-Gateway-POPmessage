package api.domain.command.request;

import api.domain.entity.Id;
import api.domain.entity.Type;
import api.domain.entity.User;

public class VoteMessagesRequest implements Request {

    private Id messageId;
    private User user;
    private Type type;

    public VoteMessagesRequest(
            User user,
            Id messageId,
            Type type
    ) {
        this.type = type;
        this.user = user;
        this.messageId = messageId;
    }

    public Id MessageId() {
        return messageId;
    }

    public User User() {
        return user;
    }

    public Type Type() {
        return type;
    }
}
