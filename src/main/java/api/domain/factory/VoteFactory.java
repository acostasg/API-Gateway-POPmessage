package api.domain.factory;

import api.domain.entity.*;

public class VoteFactory {
    public static Vote build(
            Id message,
            Id user,
            Type type
    ) {
        return new Vote(
                message,
                user,
                type
        );
    }

    public static Vote build(
            Message message,
            User user,
            Type type
    ) {
        return new Vote(
                message.ID(),
                user.ID(),
                type
        );
    }
}