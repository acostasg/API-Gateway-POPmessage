package api.infrastucture.inMemory;


import api.domain.entity.Token;
import api.domain.entity.User;

public class TokenRepository implements api.domain.infrastructure.TokenRepository {
    @Override
    public Token generateToken(User user) {
        return new Token("dummyToken");
    }

    @Override
    public boolean validateToken(Token token) {
        return true;
    }
}
