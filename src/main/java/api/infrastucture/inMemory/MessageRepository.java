package api.infrastucture.inMemory;

import api.domain.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MessageRepository implements api.domain.infrastructure.MessageRepository {

    @Override
    public List<Message> getMessagesByUser(User user) {
        return this.getMessagesByUser(user, 10);
    }

    @Override
    public List<Message> getMessagesByUser(User user, int limit) {
        List<Message> messages = new ArrayList<Message>();
        for (Integer i = 0; i < limit; i++) {
            messages.add(this.getMessageDummy());
        }
        return messages;
    }

    @Override
    public List<Message> getMessagesByLocation(Location location) {
        return this.getMessagesByLocation(location, 10);
    }

    @Override
    public List<Message> getMessagesByLocation(Location location, int limit) {
        List<Message> messages = new ArrayList<Message>();
        for (Integer i = 0; i < limit; i++) {
            messages.add(this.getMessageDummy());
        }
        return messages;
    }

    private Message getMessageDummy() {

        Random rnd = new Random();
        Integer id = rnd.nextInt();
        List<Vote> votes = getVotes(id);

        return new Message(
                new Id(id.toString()),
                new Id(UserRepository.ID_DUMMY),
                "Text for missage for testing proporse",
                new Location("41.385064", "2.173403"),
                votes,
                Status.ACTIVE
        );
    }

    private List<Vote> getVotes(Integer id) {
        List<Vote> votes = new ArrayList<Vote>();

        votes.add(
                new Vote(
                        new Id(id.toString()),
                        new Id(UserRepository.ID_DUMMY),
                        Type.POSITIVE
                )
        );
        return votes;
    }
}
