package api.infrastucture.elasticSearch.queryDSL.mappers;

import api.domain.entity.Id;
import api.domain.entity.Message;
import api.domain.entity.Type;
import api.domain.entity.Vote;
import api.domain.factory.VoteFactory;
import com.google.gson.internal.LinkedTreeMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.json.Json;
import java.util.ArrayList;

public class VoteMapper {

    public static final String DOC = "{\"doc\":";
    public static final String DOC_END = "}";

    public VoteMapper() {
    }

    public Vote builderVote(LinkedTreeMap userJson) {
        try {
            return VoteFactory.build(
                    new Id(userJson.get("messageID").toString()),
                    new Id(userJson.get("userID").toString()),
                    Type.valueOf(userJson.get("type").toString())

            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Vote> builderVotes(ArrayList<LinkedTreeMap> votesJson) {
        ArrayList<Vote> result = new ArrayList<>();
        if (null == votesJson)
            return result;

        for (LinkedTreeMap voteJson : votesJson) {
            if (null != voteJson) {
                Vote vote = this.builderVote(voteJson);
                if (null != vote)
                    result.add(vote);
            }
        }
        return result;
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