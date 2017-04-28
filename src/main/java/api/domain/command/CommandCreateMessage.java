package api.domain.command;

import api.domain.command.request.CreateMessagesRequest;
import api.domain.entity.Message;
import api.domain.infrastructure.MessageRepository;

public class CommandCreateMessage implements Command<Message, CreateMessagesRequest> {

    private MessageRepository messageRepository;

    public CommandCreateMessage(
            MessageRepository messageRepository
    ) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message execute(CreateMessagesRequest request) {
        return this.messageRepository.crateMessage(
                request.Text(),
                request.User(),
                request.Location()
        );
    }

}
