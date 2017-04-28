package api.domain.command;

import api.domain.command.request.DeleteMessagesRequest;
import api.domain.entity.Message;
import api.domain.infrastructure.MessageRepository;

public class CommandDeleteMessage implements Command<Message, DeleteMessagesRequest> {

    private MessageRepository messageRepository;

    public CommandDeleteMessage(
            MessageRepository messageRepository
    ) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message execute(DeleteMessagesRequest request) {

        Message message = this.messageRepository.getMessage(request.MessageId());

        return this.messageRepository.deleteMessage(
                request.User(),
                message
        );
    }

}
