package api.infrastucture.elasticSearch.queryDSL.mappers;

import api.domain.entity.Id;
import api.domain.entity.Message;
import api.domain.entity.Status;
import api.domain.factory.MessageFactory;
import api.domain.factory.UserFactory;
import io.searchbox.core.SearchResult;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessageMapper {

    private final VoteMapper voteMapper = new VoteMapper();

    public MessageMapper() {
    }

    public Message builderMessage(JSONObject userJson) {
        try {
            return MessageFactory.buildMessage(
                    new Id(userJson.get("ID").toString()),
                    UserFactory.buildByMessage(
                            new Id(userJson.get("user.ID").toString()),
                            userJson.get("user.name").toString()
                    ),
                    userJson.get("text").toString(),
                    GeoLocation.decode(userJson.get("location").toString()),
                    this.voteMapper.builderMessages((ArrayList)userJson.get("votes")),
                    Status.valueOf(userJson.get("status").toString())

            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Message> builderMessages(List<SearchResult.Hit<JSONObject, Void>> messages) {
        ArrayList<Message> result = new ArrayList<>();
        for (SearchResult.Hit<JSONObject, Void> message : messages) {
            result.add(this.builderMessage(message.source));
        }
        return result;
    }

    public String encodeMessage(Message message) {
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
        obj.put("status", message.Status().toString());
        return obj.toJSONString();
    }

}