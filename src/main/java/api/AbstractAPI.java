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
import api.infrastucture.inMemory.MessageRepository;
import api.infrastucture.inMemory.TokenRepository;
import api.infrastucture.inMemory.UserRepository;

import java.util.List;

abstract class AbstractAPI {

    User getUserByToken(String token) {
        CommandGetUserByToken userCaseGetUserByToken = new CommandGetUserByToken(
                new UserRepository(),
                new CommandValidateToken(
                        new TokenRepository()
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
    ) {
        User user = this.getUserByToken(token);

        CommandVoteMessage useCase = new CommandVoteMessage(
                new MessageRepository()
        );

        return useCase.execute(
                new VoteMessagesRequest(
                        user,
                        new Id(messageId),
                        type
                )
        );
    }

    List<Message> getMessagesByUser(api.domain.entity.User user) {
        CommandGetMessagesByUser userCaseGetMessagesByUser = new CommandGetMessagesByUser(
                new MessageRepository()
        );

        return userCaseGetMessagesByUser.execute(
                new GetMessagesByUserRequest(
                        user
                )
        );
    }

}
