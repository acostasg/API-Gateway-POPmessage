package api.infrastucture.elasticSearch.queryDSL.mappers;

import api.domain.entity.Id;
import api.domain.entity.Message;
import api.domain.entity.Status;
import api.domain.entity.Vote;
import api.domain.factory.MessageFactory;
import api.domain.factory.UserFactory;
import io.searchbox.core.SearchResult;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.json.Json;
import java.util.ArrayList;
import java.util.List;

public class MessageMapper {

    public static final String DOC = "{\"doc\":";
    public static final String DOC_END = "}";

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
                    new ArrayList<Vote>(), //TODO add votes
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

    public String encodeMessageVotes(Message message) {
        JSONObject obj = new JSONObject();

        JSONArray votes = new JSONArray();

        for (Vote vote : message.Votes()) {
            votes.add(
                    Json.createObjectBuilder()
                            .add("messageID", vote.MessageID().Id())
                            .add("userID", vote.UserID().Id())
                            .add("type", vote.Type().toString()).build()
            );
        }

        obj.put("votes", votes.toString());
        return DOC + obj.toJSONString() + DOC_END;
    }

}