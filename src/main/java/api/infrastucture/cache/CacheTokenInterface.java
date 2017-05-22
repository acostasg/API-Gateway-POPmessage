package api.infrastucture.cache;

import api.domain.entity.Token;
import api.domain.entity.User;

public interface CacheTokenInterface {

    void setUser(User user, Token token);

    User getUser(Token token);

    boolean hasToken(Token token);

    void deleteUser(Token token);
}
