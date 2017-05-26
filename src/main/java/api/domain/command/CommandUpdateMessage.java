package api.domain.command;

import api.domain.command.request.UpdateMessagesRequest;
import api.domain.entity.Message;
import api.domain.factory.MessageFactory;
import api.domain.infrastructure.MessageRepository;

public class CommandUpdateMessage implements Command<Message, UpdateMessagesRequest> {

    private MessageRepository messageRepository;

    public CommandUpdateMessage(
            MessageRepository messageRepository
    ) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message execute(UpdateMessagesRequest request) {

        Message message = this.messageRepository.getMessage(request.messageID());

        if (isMessageFromUser(request, message))
            return null;

        Message newMessage = MessageFactory.buildMessage(
                message.ID(),
                message.User(),
                request.text(),
                message.Location(),
                message.votes(),
                message.status()
        );

        return this.messageRepository.updateMessage(
                newMessage
        );
    }

    private boolean isMessageFromUser(UpdateMessagesRequest request, Message message) {
        return null == message || !message.userId().equals(request.user().getId());
    }

}
