package api.infrastucture.elasticSearch.queryDSL.mappers;

import api.domain.entity.Id;
import api.domain.entity.Status;
import api.domain.entity.User;
import api.domain.factory.UserFactory;
import io.searchbox.core.SearchResult;
import org.json.simple.JSONObject;

import java.util.Date;

public class UserMapper {

    public User builderUser(JSONObject userJson, Date date) {
        return UserFactory.build(
                new Id(userJson.get("ID").toString()),
                userJson.get("name").toString(),
                userJson.get("userLogin").toString(),
                userJson.get("password").toString(),
                Status.valueOf(userJson.get("status").toString()),
                date
        );
    }

    public User builderUserSearch(SearchResult.Hit<JSONObject, Void> user, Date date) {
        return UserFactory.build(
                new Id(user.id),
                user.source.get("name").toString(),
                user.source.get("userLogin").toString(),
                user.source.get("password").toString(),
                Status.valueOf(user.source.get("status").toString()),
                date);
    }

    public String encoderUser(User user, String date) {
        JSONObject obj = new JSONObject();
        obj.put("ID", user.ID().Id());
        obj.put("name", user.Name());
        obj.put("userLogin", user.UserLogin());
        obj.put("password", user.Password());
        obj.put("status", user.Status().toString());
        obj.put("crateAt", date);
        return obj.toJSONString();

    }
}
