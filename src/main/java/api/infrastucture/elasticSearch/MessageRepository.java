package api.infrastucture.elasticSearch;

import api.domain.entity.*;
import api.domain.factory.MessageFactory;
import api.domain.factory.UserFactory;
import api.infrastucture.elasticSearch.queryDSL.MessageByUserDSL;
import api.infrastucture.elasticSearch.queryDSL.UserByTokenDSL;
import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.SearchResult;
import org.glassfish.hk2.utilities.reflection.Logger;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MessageRepository extends AbstractElasticSearchRepository implements api.domain.infrastructure.MessageRepository {

    private static final String index = "message_index";
    private static final String type = "message";

    @Override
    public List<Message> getMessagesByUser(User user) {
        return this.getMessagesByUser(user, 10);
    }

    @Override
    public List<Message> getMessagesByUser(User user, int limit) {
        try {

            startConnection();

            SearchResult response = this.elasticSearchClient.
                    prepareSearch(index).
                    setType(type).
                    executeQuery(
                            MessageByUserDSL.get(user)
                    );


            if (!response.isSucceeded() || response.getTotal() <= 0) {
                stopConnection();
                return null;
            }

            List<SearchResult.Hit<JSONObject, Void>> messages = response.getHits(JSONObject.class);
            return builderMessages(messages);
        } catch (Exception e) {
            Logger.printThrowable(e);
            e.printStackTrace();
        }

        return null;
    }

    private ArrayList<Message> builderMessages(List<SearchResult.Hit<JSONObject, Void>> messages) {
        ArrayList<Message> result = new ArrayList<>();
        for (SearchResult.Hit<JSONObject, Void> message: messages) {
            result.add(this.BuilderMessage(message.source));
        }
        return null;
    }

    @Override
    public List<Message> getMessagesByLocation(Location location) {
        return this.getMessagesByLocation(location, 10);
    }

    @Override
    public List<Message> getMessagesByLocation(Location location, int limit) {
        startConnection();
        /*SearchResponse response = this.elasticSearchClient.
                prepareSearch(index).
                setType(type).
                executeQuery(
                        QueryBuilders.
                                boolQuery().
                                must(QueryBuilders.termQuery("location.lat", location.Lat())).
                                must(QueryBuilders.termQuery("location.lon", location.Lon()))
                );

        stopConnection();
        if (response.getHits().totalHits() <= 0) {
            return null;
        }

        return builderMessages(response);*/
        return null;
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
            return BuilderMessage(userJson);

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Message BuilderMessage(JSONObject userJson) {
        return MessageFactory.buildMessage(
                new Id(userJson.get("ID").toString()),
                UserFactory.buildByMessage(
                        new Id(userJson.get("user.ID").toString()),
                        userJson.get("user.name").toString()
                ),
                userJson.get("text").toString(),
                new Location(
                        userJson.get("location.lat").toString(), //TODO ???
                        userJson.get("location.lon").toString()
                ),
                new ArrayList<Vote>(), //TODO add votes
                Status.valueOf(userJson.get("status").toString())

        );
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

            JSONObject obj = new JSONObject();
            obj.put("ID", message.ID().Id());
            obj.put("user.ID", message.user().ID().Id());
            obj.put("user.name", message.user().Name());
            obj.put("ID", message.ID().Id());
            obj.put("text", message.Text());
            JSONObject locationJson = new JSONObject();
            locationJson.put("lat", Float.parseFloat(message.Location().Lat()));
            locationJson.put("lon", Float.parseFloat(message.Location().Lon()));
            obj.put("location", locationJson);
            obj.put("status", user.Status().toString());

            DocumentResult documentResult = this.elasticSearchClient
                    .prepareSearch(index)
                    .setType(type)
                    .set(obj.toJSONString(), message.ID().Id());

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
