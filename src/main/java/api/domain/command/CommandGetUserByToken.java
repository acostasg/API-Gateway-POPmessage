package api.domain.command;

import api.domain.command.request.GetUserByTokenRequest;
import api.domain.entity.User;
import api.domain.infrastructure.UserRepository;

public class CommandGetUserByToken implements Command<User, GetUserByTokenRequest> {

    private UserRepository userRepository;

    public CommandGetUserByToken(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    public User execute(GetUserByTokenRequest request) {
        return this.userRepository.getUserByToken(request.Token());
    }

}
