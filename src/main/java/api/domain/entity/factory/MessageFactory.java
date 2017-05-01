package api.domain.entity.factory;

import api.domain.entity.*;

import java.util.List;

public class MessageFactory {
    public static Message buildMessage(Id id, Id userID, String text, Location location, List<Vote> votes, Status status) {
        return new Message(
                id,
                userID,
                text,
                location,
                votes,
                status
        );
    }
}