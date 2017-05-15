package api.domain.factory;

import api.domain.entity.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageFactory {
    public static Message buildMessage(
            Id id,
            User user,
            String text,
            Location location,
            List<Vote> votes,
            Status status
    ) {
        return new Message(
                id,
                user,
                text,
                location,
                votes,
                status
        );
    }

    public static Message buildMessage(
            Id id,
            User user,
            String text,
            Location location,
            List<Vote> votes,
            Status status,
            Date date
    ) {
        return new Message(
                id,
                user,
                text,
                location,
                votes,
                status,
                date
        );
    }

    public static Message build(
            Id id,
            User user,
            String text,
            Location location,
            Status status
    ) {
        return new Message(
                id,
                user,
                text,
                location,
                new ArrayList<Vote>(),
                status
        );
    }
}