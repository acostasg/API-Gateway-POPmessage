package api.infrastucture.elasticSearch;

import api.domain.entity.*;
import api.domain.factory.MessageFactory;
import api.infrastucture.elasticSearch.queryDSL.MessageByLocationUserDSL;
import api.infrastucture.elasticSearch.queryDSL.MessageByUserDSL;
import api.infrastucture.elasticSearch.queryDSL.mappers.MessageMapper;
import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.SearchResult;
import org.glassfish.hk2.utilities.reflection.Logger;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.UUID;

public class MessageRepository extends AbstractElasticSearchRepository implements api.domain.infrastructure.MessageRepository {

    private static final String index = "message_index";
    private static final String type = "message";
    private final MessageMapper messageMapper = new MessageMapper();

    @Override
    public List<Message> getMessagesByUser(User user) {
        return this.getMessagesByUser(user, 10);
    }

    @Override
    public List<Message> getMessagesByUser(User user, int limit) {
        String queryDSL = MessageByUserDSL.get(user, 0, limit);
        return this.getMessagesByQueryDSl(queryDSL);
    }

    private List<Message> getMessagesByQueryDSl(String queryDSL) {
        try {

            startConnection();

            SearchResult response = this.elasticSearchClient.
                    prepareSearch(index).
                    setType(type).
                    executeQuery(
                            queryDSL
                    );


            if (!response.isSucceeded()) {
                stopConnection();
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
    public List<Message> getMessagesByLocation(Location location) {
        return this.getMessagesByLocation(location, 10);
    }

    @Override
    public List<Message> getMessagesByLocation(Location location, int limit) {
        String queryDSL = MessageByLocationUserDSL.get(location, 0, limit);
        return this.getMessagesByQueryDSl(queryDSL);
    }

    @Override
    public Message getMessage(Id messageId) {
        try {
            startConnection();

            JestResult messageResponse = this.elasticSearchClient
                    .prepareSearch(index)
                    .setType(type)
                    .get(
                            messageId.Id()
                    );

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

            startConnection();

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
                    .set(this.messageMapper.encodeMessage(message, user), message.ID().Id());

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
        return null;
    }

    @Override
    public Message addVoteToMessage(User user, Message message, Type status) {
        return null;
    }
}
