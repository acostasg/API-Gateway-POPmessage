package api.domain.command;

import api.domain.command.request.LoginUserRequest;
import api.domain.entity.Token;
import api.domain.entity.User;
import api.domain.infrastructure.TokenRepository;
import api.domain.infrastructure.UserRepository;

public class CommandLogin implements Command<Token, LoginUserRequest> {

    private UserRepository userRepository;
    private TokenRepository tokenRepository;

    public CommandLogin(
            UserRepository userRepository,
            TokenRepository tokenRepository
    ) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token execute(LoginUserRequest request) {
        User user = this.userRepository.loginUser(
                request.UserName(),
                request.Password()
        );
        if (user == null) {
            return null;
        }
        return this.tokenRepository.generateToken(user);
    }

}
