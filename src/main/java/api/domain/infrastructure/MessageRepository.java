package api.domain.infrastructure;


import api.domain.entity.Message;
import api.domain.entity.User;

import java.util.List;

public interface MessageRepository {

    List<Message> getMessagesByUser(User user);

    List<Message> getMessagesByUser(User user, int limit);

}
