package api.domain.command;

import api.domain.command.request.RegisterUserRequest;
import api.domain.entity.User;
import api.domain.exceptions.UserInUse;
import api.domain.infrastructure.UserRepository;

public class CommandRegisterUser implements Command<User, RegisterUserRequest> {

    private UserRepository userRepository;

    public CommandRegisterUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User execute(RegisterUserRequest request) throws UserInUse {

        if (!request.PrivacyPolicy()) {
            return null;
        }

        return this.userRepository.registerUser(
                request.Name(),
                request.DateOfBirth(),
                request.UserName(),
                request.Password()
        );
    }

}
