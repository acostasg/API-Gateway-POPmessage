package api.infrastucture.elasticSearch;

import api.domain.entity.*;
import api.infrastucture.elasticSearch.queryDSL.MessageByUserDSL;
import io.searchbox.core.SearchResult;
import org.glassfish.hk2.utilities.reflection.Logger;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

            SearchResult.Hit<JSONObject, Void> messages = response.getFirstHit(JSONObject.class);
            stopConnection();
            /*return Mess.build(
                    new Id(user.id),
                    user.source.get("name").toString(),
                    user.source.get("userLogin").toString(),
                    user.source.get("password").toString(),
                    Status.valueOf(user.source.get("status").toString()),
                    getDateFromString(user.source.get("crateAt").toString())
            );*/
        } catch (Exception e) {
            Logger.printThrowable(e);
            e.printStackTrace();
        }

        return null;
    }

    private ArrayList<Message> builderMessages(SearchResult response) {
       /* ArrayList<Message> messages = new ArrayList<Message>();
        while (response.getHits().iterator().hasNext()) {
            SearchHit searchHit = response.getHits().iterator().next();
            messages.add(
                    MessageFactory.buildMessage(
                            new Id(searchHit.getId()),
                            new User(
                                    new Id(searchHit.field("user.ID").toString()),
                                    searchHit.field("user.name").toString()
                            ),
                            searchHit.field("text").toString(),
                            new Location(
                                    searchHit.field("location.lat").getValue().toString(),
                                    searchHit.field("location.lon").getValue().toString()
                            ),
                            new ArrayList<Vote>(),
                            Status.ACTIVE
                    )
            );
        }
        return messages;*/
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
        startConnection();
       /* GetResponse response = this.elasticSearchClient.get(
                messageId.Id(),
                index,
                type
        );
        stopConnection();

        if (response.isSourceEmpty()) {
            return null;
        }

        return MessageFactory.buildMessage(
                new Id(response.getId()),
                new User(
                        new Id(response.getField("user.ID").toString()),
                        response.getField("user.name").toString()
                ),
                response.getField("text").toString(),
                new Location(
                        response.getField("location.lat").getValue().toString(),
                        response.getField("location.lon").getValue().toString()
                ),
                new ArrayList<Vote>(),
                Status.ACTIVE
        );*/
        return null;
    }

    @Override
    public Message crateMessage(String text, User user, Location location) {
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
