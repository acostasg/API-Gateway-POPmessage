package api.domain.command;

import api.domain.command.request.GetMessagesByUserRequest;
import api.domain.entity.Message;
import api.domain.infrastructure.MessageRepository;

import java.util.List;

public class CommandGetMessagesByUser implements Command<List<Message>, GetMessagesByUserRequest> {

    private MessageRepository messageRepository;

    public CommandGetMessagesByUser(
            MessageRepository messageRepository
    ) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> execute(GetMessagesByUserRequest request) {
        return this.messageRepository.getMessagesByUser(request.user());
    }

}
