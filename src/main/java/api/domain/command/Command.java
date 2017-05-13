package api.domain.command;

import api.domain.command.request.Request;
import api.domain.exceptions.UserInUse;

public interface Command<T, R extends Request> {
    T execute(R request) throws UserInUse;
}
