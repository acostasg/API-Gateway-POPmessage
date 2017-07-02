package api.domain.infrastructure;


import api.domain.entity.*;

import java.util.List;

public interface MessageRepository {

    List<Message> getMessagesByUser(User user, int last);

    List<Message> getMessagesByUser(User user, int last, int limit);

    List<Message> getMessagesByLocation(Location location, int last);

    List<Message> getMessagesByLocation(Location location, int last, int limit);

    Message getMessage(Id messageId);

    Message crateMessage(String text, User user, Location location);

    Message deleteMessage(User user, Message message);

    Message addVoteToMessage(User user, Message message, Type status);

    Message updateMessage(Message message);

}
