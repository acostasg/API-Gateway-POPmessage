package api.domain.command;

import api.domain.command.request.GetUserByTokenRequest;
import api.domain.command.request.ValidateTokenRequest;
import api.domain.entity.Token;
import api.domain.entity.User;
import api.domain.infrastructure.UserRepository;

public class CommandGetUserByToken implements Command<User, GetUserByTokenRequest> {

    private UserRepository userRepository;
    private CommandValidateToken useCaseValidateToken;

    public CommandGetUserByToken(
            UserRepository userRepository,
            CommandValidateToken useCaseValidateToken
    ) {
        this.userRepository = userRepository;
        this.useCaseValidateToken = useCaseValidateToken;
    }

    @Override
    public User execute(GetUserByTokenRequest request) {

        Token token = this.useCaseValidateToken.execute(
                new ValidateTokenRequest(
                        request.token().hash()
                )
        );

        if (token.isEmpty()) {
            return null;
        }

        return this.userRepository.getUserByToken(token);
    }

}
