package api.infrastucture.elasticSearch;

import api.domain.entity.*;
import api.domain.factory.MessageFactory;
import api.domain.factory.VoteFactory;
import api.infrastucture.elasticSearch.queryDSL.AddVoteToMessageDSL;
import api.infrastucture.elasticSearch.queryDSL.MessageByLocationUserDSL;
import api.infrastucture.elasticSearch.queryDSL.MessageByUserDSL;
import api.infrastucture.elasticSearch.queryDSL.UpdateMessageDSL;
import api.infrastucture.elasticSearch.queryDSL.mappers.MessageMapper;
import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.SearchResult;
import org.glassfish.hk2.utilities.reflection.Logger;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class MessageRepository extends AbstractElasticSearchRepository implements api.domain.infrastructure.MessageRepository {

    public static final int LIMIT = 10;
    private static final String index = "message_index";
    private static final String type = "message";
    private final MessageMapper messageMapper = new MessageMapper();

    @Override
    public List<Message> getMessagesByUser(User user, int last) {
        return this.getMessagesByUser(user, last, LIMIT);
    }

    @Override
    public List<Message> getMessagesByUser(User user, int last, int limit) {
        String queryDSL = MessageByUserDSL.get(user, last, limit);
        return this.getMessagesByQueryDSl(queryDSL);
    }

    private List<Message> getMessagesByQueryDSl(String queryDSL) {
        try {
            SearchResult response = this.elasticSearchClient.
                    prepareSearch(index).
                    setType(type).
                    executeQuery(
                            queryDSL
                    );


            if (!response.isSucceeded()) {
                return null;
            }

            List<SearchResult.Hit<JSONObject, Void>> messages = response.getHits(JSONObject.class);
            return this.messageMapper.builderMessages(messages);
        } catch (Exception e) {
            Logger.printThrowable(e);
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Message> getMessagesByLocation(Location location, int last) {
        String queryDSL = MessageByLocationUserDSL.get(location, last, LIMIT);
        return this.getMessagesByQueryDSl(queryDSL);
    }

    @Override
    public List<Message> getMessagesByLocation(Location location, int last, int limit) {
        String queryDSL = MessageByLocationUserDSL.get(location, last, limit);
        return this.getMessagesByQueryDSl(queryDSL);
    }

    @Override
    public Message getMessage(Id messageId) {
        try {
            JestResult messageResponse = this.elasticSearchClient
                    .prepareSearch(index)
                    .setType(type)
                    .get(messageId.Id());

            if (!messageResponse.isSucceeded()) {
                return null;
            }

            JSONObject userJson = messageResponse.getSourceAsObject(JSONObject.class);
            return messageMapper.builderMessage(userJson);

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Message crateMessage(String text, User user, Location location) {

        try {

            UUID uuid = UUID.randomUUID();

            Message message = MessageFactory.build(
                    new Id(uuid.toString()),
                    user,
                    text,
                    location,
                    Status.ACTIVE
            );

            DocumentResult documentResult = this.elasticSearchClient
                    .prepareSearch(index)
                    .setType(type)
                    .set(
                            this.messageMapper.encodeMessage(
                                    message
                            ),
                            message.ID().Id()
                    );

            if (documentResult.isSucceeded()) {
                return message;
            }

        } catch (java.io.IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public Message deleteMessage(User user, Message message) {
        message.delete();
        try {
            this.elasticSearchClient
                    .prepareSearch(index)
                    .setType(type)
                    .set(
                            this.messageMapper.encodeMessage(
                                    message
                            ),
                            message.ID().Id()
                    );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return message;

    }

    @Override
    public Message addVoteToMessage(User user, Message message, Type typeMessage) {
        Vote vote = VoteFactory.build(
                message,
                user,
                typeMessage
        );

        try {
            this.elasticSearchClient
                    .prepareSearch(index)
                    .setType(type)
                    .put(
                            AddVoteToMessageDSL.get(vote),
                            message.ID().Id()
                    );
        } catch (IOException e) {
            e.printStackTrace();
        }

        message.addVote(
                vote
        );

        return message;
    }

    @Override
    public Message updateMessage(Message message) {
        try {
            this.elasticSearchClient
                    .prepareSearch(index)
                    .setType(type)
                    .put(
                            UpdateMessageDSL.get(
                                    message
                            ),
                            message.ID().Id()
                    );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return message;
    }
}
