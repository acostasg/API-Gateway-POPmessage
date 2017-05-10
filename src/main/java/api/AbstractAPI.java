package api;

import api.domain.command.CommandGetMessagesByUser;
import api.domain.command.CommandGetUserByToken;
import api.domain.command.CommandValidateToken;
import api.domain.command.CommandVoteMessage;
import api.domain.command.request.GetMessagesByUserRequest;
import api.domain.command.request.GetUserByTokenRequest;
import api.domain.command.request.VoteMessagesRequest;
import api.domain.entity.Id;
import api.domain.entity.Message;
import api.domain.entity.Type;
import api.domain.entity.User;
import api.domain.exceptions.InvalidUser;
import api.domain.infrastructure.MessageRepository;
import api.domain.infrastructure.TokenRepository;
import api.infrastucture.inMemory.UserRepository;

import javax.inject.Inject;
import java.util.List;

abstract class AbstractAPI {

    @Inject
    protected MessageRepository messageRepository;
    @Inject
    protected TokenRepository tokenRepository;

    User getUserByToken(String token) {
        CommandGetUserByToken userCaseGetUserByToken = new CommandGetUserByToken(
                new UserRepository(),
                new CommandValidateToken(
                        this.tokenRepository
                )
        );

        return userCaseGetUserByToken.execute(
                new GetUserByTokenRequest(
                        token
                )
        );
    }

    Message getAddVoteToMessage(
            String messageId,
            String token,
            Type type
    ) throws InvalidUser {
        User user = this.getUserByToken(token);

        if (null == user) {
            throw new InvalidUser("Invalid User");
        }

        CommandVoteMessage useCase = new CommandVoteMessage(
                this.messageRepository
        );

        return useCase.execute(
                new VoteMessagesRequest(
                        user,
                        new Id(messageId),
                        type
                )
        );
    }

    List<Message> getMessagesByUser(User user) {
        CommandGetMessagesByUser userCaseGetMessagesByUser = new CommandGetMessagesByUser(
                this.messageRepository
        );

        return userCaseGetMessagesByUser.execute(
                new GetMessagesByUserRequest(
                        user
                )
        );
    }

}
