package api.domain.command;

import api.domain.command.request.GetMessagesByLocationRequest;
import api.domain.command.request.GetMessagesByUserRequest;
import api.domain.entity.Message;
import api.domain.infrastructure.MessageRepository;

import java.util.List;

public class CommandGetMessagesByLocation implements Command<List<Message>, GetMessagesByLocationRequest> {

    private MessageRepository messageRepository;

    public CommandGetMessagesByLocation(
            MessageRepository messageRepository
    ) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> execute(GetMessagesByLocationRequest request) {
        return this.messageRepository.getMessagesByLocation(request.location());
    }

}
