package api.domain.infrastructure;

import api.domain.entity.Token;
import api.domain.entity.User;
import api.domain.exceptions.UserInUse;

public interface UserRepository {

    User registerUser(String name, String dateOfBirth, String userName, String password) throws UserInUse;

    User loginUser(String userName, String password);

    User getUserByToken(Token token);

}
