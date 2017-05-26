package api.domain.command.request;

import api.domain.entity.Id;
import api.domain.entity.User;

public class UpdateMessagesRequest implements Request {

    private Id messageID;
    private String text;
    private User user;

    public UpdateMessagesRequest(
            Id messageID,
            String text,
            User user
    ) {
        this.messageID = messageID;
        this.text = text;
        this.user = user;
    }


    public Id messageID() {
        return messageID;
    }

    public String text() {
        return text;
    }

    public User user() {
        return user;
    }
}
