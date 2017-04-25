package api.domain.infrastructure;

import api.domain.entity.Token;
import api.domain.entity.User;

public interface TokenRepository {

    Token generateToken(User user);

    boolean validateToken(Token token);

}
