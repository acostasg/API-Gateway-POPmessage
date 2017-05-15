package api.infrastucture.elasticSearch.queryDSL.mappers;

import api.domain.entity.Id;
import api.domain.entity.Status;
import api.domain.entity.User;
import api.domain.factory.UserFactory;
import api.domain.service.FormatDataService;
import io.searchbox.core.SearchResult;
import org.json.simple.JSONObject;

public class UserMapper {

    public User builderUser(JSONObject userJson) {
        return UserFactory.build(
                new Id(userJson.get("ID").toString()),
                userJson.get("name").toString(),
                userJson.get("userLogin").toString(),
                userJson.get("password").toString(),
                Status.valueOf(userJson.get("status").toString()),
                FormatDataService.getDateFromString(userJson.get("crateAt").toString())
        );
    }

    public User builderUserSearch(SearchResult.Hit<JSONObject, Void> user) {
        return UserFactory.build(
                new Id(user.id),
                user.source.get("name").toString(),
                user.source.get("userLogin").toString(),
                user.source.get("password").toString(),
                Status.valueOf(user.source.get("status").toString()),
                FormatDataService.getDateFromString(user.source.get("crateAt").toString())
        );
    }

    public String encoderUser(User user) {
        JSONObject obj = new JSONObject();
        obj.put("ID", user.ID().Id());
        obj.put("name", user.Name());
        obj.put("userLogin", user.userLogin());
        obj.put("password", user.password());
        obj.put("status", user.status().toString());
        obj.put("crateAt", FormatDataService.getDateFromDate(user.date()));
        return obj.toJSONString();

    }
}
