package api.infrastucture.elasticSearch;

import api.domain.entity.*;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.ArrayList;
import java.util.List;

public class MessageRepository extends AbstractElasticSearchRepository implements api.domain.infrastructure.MessageRepository {

    private static final String index = "message_index";
    private static final String type = "message";

    @Override
    public List<Message> getMessagesByUser(User user) {

        SearchResponse response = this.elasticSearchClient.
                prepareSearch(index).
                setType(type).
                executeQuery(QueryBuilders.termQuery("multi", "test"));


        return null;
    }

    @Override
    public List<Message> getMessagesByUser(User user, int limit) {
        return null;
    }

    @Override
    public List<Message> getMessagesByLocation(Location location) {
        return null;
    }

    @Override
    public List<Message> getMessagesByLocation(Location location, int limit) {
        return null;
    }

    @Override
    public Message getMessage(Id messageId) {
        GetResponse response = this.elasticSearchClient.get(
               messageId.Id(),
               index,
               type
       );

        if (response.isSourceEmpty()){
            return null;
        }

        return new Message(
         new Id(response.getId()),
                new Id(response.getField("user.ID").toString()),
                response.getField("text").toString(),
                new Location(
                        response.getField("location.lat").toString(),
                        response.getField("location.lon").toString()
                ),
                new ArrayList<Vote>(),
                Status.ACTIVE
        );
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
