package api.domain.infrastructure;

import api.domain.entity.Token;
import api.domain.entity.User;

public interface UserRepository {

    User registerUser(String name, String dateOfBirth, String userName, String password );

    User loginUser(String userName, String password);

    User getUserByToken(Token token);

}
