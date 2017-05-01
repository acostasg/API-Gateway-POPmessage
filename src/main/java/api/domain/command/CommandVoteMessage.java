package api.domain.command;

import api.domain.command.request.VoteMessagesRequest;
import api.domain.entity.Message;
import api.domain.infrastructure.MessageRepository;

public class CommandVoteMessage implements Command<Message, VoteMessagesRequest> {

    private MessageRepository messageRepository;

    public CommandVoteMessage(
            MessageRepository messageRepository
    ) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message execute(VoteMessagesRequest request) {

        Message message = this.messageRepository.getMessage(request.MessageId());

        if (message == null) {
            return null;
        }


        return this.messageRepository.addVoteToMessage(
                request.User(),
                message,
                request.Type()
        );
    }

}
