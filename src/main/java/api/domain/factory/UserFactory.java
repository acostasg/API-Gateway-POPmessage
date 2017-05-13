package api.domain.factory;

import api.domain.entity.Id;
import api.domain.entity.Status;
import api.domain.entity.User;

import java.util.Date;

public class UserFactory {
    public static User build(
            Id ID,
            String name,
            String userLogin,
            String password,
            Status status,
            Date date
    ) {
        return new User(
                ID,
                name,
                userLogin,
                password,
                status,
                date
        );
    }

    public static User buildByMessage(
            Id ID,
            String name
    ) {
        return new User(
                ID,
                name,
                null,
                null,
                null,
                null
        );
    }
}