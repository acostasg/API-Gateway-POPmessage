package api.domain.command;

import api.domain.command.request.Request;

public interface Command<T, R extends Request> {
    T execute(R request);
}
