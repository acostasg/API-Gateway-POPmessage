package api.domain.factory;

import api.domain.entity.Id;
import api.domain.entity.Type;
import api.domain.entity.Vote;

public class VoteFactory {
    public static Vote build(
            Id messageId,
            Id userId,
            Type type
    ) {
        return new Vote(
                messageId,
                userId,
                type
        );
    }
}