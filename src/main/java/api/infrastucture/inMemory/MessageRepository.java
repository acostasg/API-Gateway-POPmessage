package api.infrastucture.inMemory;

import api.domain.entity.*;
import api.domain.factory.MessageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MessageRepository implements api.domain.infrastructure.MessageRepository {

    static final private String DUMMY_ID = "Dummy Id";
    static final private String DUMMY_NAME = "Dummy Name";

    private final Random rnd;

    public MessageRepository() {
        rnd = new Random();
    }

    @Override
    public List<Message> getMessagesByUser(User user, int last) {
        return this.getMessagesByUser(user, 10);
    }

    @Override
    public List<Message> getMessagesByUser(User user, int last, int limit) {
        List<Message> messages = new ArrayList<Message>();
        for (Integer i = 0; i < limit; i++) {
            messages.add(this.getMessageDummy());
        }
        return messages;
    }

    @Override
    public List<Message> getMessagesByLocation(Location location, int last) {
        return this.getMessagesByLocation(location, last, 10);
    }

    @Override
    public List<Message> getMessagesByLocation(Location location, int last, int limit) {
        List<Message> messages = new ArrayList<Message>();
        for (Integer i = 0; i < limit; i++) {
            messages.add(this.getMessageDummy());
        }
        return messages;
    }

    @Override
    public Message getMessage(Id messageId) {
        return this.getMessageDummy(messageId.Id());
    }

    @Override
    public Message crateMessage(String text, User user, Location location) {
        return this.getMessageDummy(DUMMY_ID, user.ID().Id(), text, location);
    }

    @Override
    public Message deleteMessage(User user, Message message) {
        return MessageFactory.buildMessage(
                message.ID(),
                user,
                message.Text(),
                message.Location(),
                message.votes(),
                Status.DELETED
        );
    }

    @Override
    public Message addVoteToMessage(User user, Message message, Type type) {
        ArrayList<Vote> votes = new ArrayList<Vote>();
        votes.add(new Vote(
                message.ID(),
                user.ID(),
                type
        ));
        return MessageFactory.buildMessage(
                message.ID(),
                user,
                message.Text(),
                message.Location(),
                votes,
                Status.ACTIVE
        );
    }

    private Message getMessageDummy() {
        Integer id = rnd.nextInt();
        return this.getMessageDummy(id.toString());
    }

    private Message getMessageDummy(String id) {
        return this.getMessageDummy(
                id,
                UserRepository.ID_DUMMY,
                "Text for missage for testing proporse",
                new Location("41.385064", "2.173403")
        );
    }

    private Message getMessageDummy(String id, String userId, String text, Location location) {
        return MessageFactory.buildMessage(
                new Id(id),
                new User(new Id(userId), DUMMY_NAME),
                text,
                location,
                getVotes(),
                Status.ACTIVE
        );
    }

    private List<Vote> getVotes() {
        Integer id = rnd.nextInt();
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

    @Override
    public Message updateMessage(Message message) {
        return message;
    }
}
