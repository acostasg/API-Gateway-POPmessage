package api.domain.command;

import api.domain.command.request.ValidateTokenRequest;
import api.domain.entity.Token;
import api.domain.infrastructure.TokenRepository;

public class CommandValidateToken implements Command<Token, ValidateTokenRequest> {

    private TokenRepository tokenRepository;

    public CommandValidateToken(
            TokenRepository tokenRepository
    ) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token execute(ValidateTokenRequest request) {

        if (request.token().isEmpty()) {
            return null;
        }

        return this.tokenRepository.validateToken(request.token());
    }

}
