package api.domain.infrastructure;

import api.domain.entity.Token;
import api.domain.entity.User;

public interface TokenRepository {

    Token generateToken(User user);

    Token validateToken(Token token);

    void deleteToken(User user, Token token);

}
