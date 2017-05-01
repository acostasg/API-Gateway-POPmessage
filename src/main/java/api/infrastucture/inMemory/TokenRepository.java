package api.infrastucture.inMemory;


import api.domain.entity.Token;
import api.domain.entity.User;

public class TokenRepository implements api.domain.infrastructure.TokenRepository {

    private final static String DUMMY_TOKEN = "sakjfh97325437hskfsdfd_sdkjfsjf1283763339564921734sdfbdsj";


    @Override
    public Token generateToken(User user) {
        return new Token(DUMMY_TOKEN);
    }

    @Override
    public Token validateToken(Token token) {

        if (token.isEmpty()) {
            return null;
        }

        if (token.isHashEqual(DUMMY_TOKEN)) {
            return new Token(DUMMY_TOKEN);
        }

        return null;
    }
}
