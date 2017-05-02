package api.domain.command;

import api.domain.command.request.LogOutUserRequest;
import api.domain.entity.Token;
import api.domain.infrastructure.TokenRepository;

public class CommandLogout implements Command<Token, LogOutUserRequest> {

    private TokenRepository tokenRepository;

    public CommandLogout(
            TokenRepository tokenRepository
    ) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token execute(LogOutUserRequest request) {
        this.tokenRepository.deleteToken(request.User(), request.Token());
        return null;
    }

}
