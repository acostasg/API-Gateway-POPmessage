package api.infrastucture.elasticSearch;

import api.domain.entity.Id;
import api.domain.entity.Status;
import api.domain.entity.Token;
import api.domain.entity.User;
import api.domain.factory.UserFactory;
import api.infrastucture.elasticSearch.queryDSL.EncodeWrapper;
import api.infrastucture.elasticSearch.queryDSL.LoginUserDSL;
import api.infrastucture.elasticSearch.queryDSL.UserByTokenDSL;
import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.SearchResult;
import org.glassfish.hk2.utilities.reflection.Logger;
import org.json.simple.JSONObject;

import java.util.UUID;

public class UserRepository extends AbstractElasticSearchRepository implements api.domain.infrastructure.UserRepository {

    private static final String index = "user_index";
    private static final String type = "user";

    private static final String index_token = "token_index";
    private static final String type_toke = "token";


    @Override
    public User registerUser(String name, String dateOfBirth, String userName, String password) {
        try {

            startConnection();

            UUID uuid = UUID.randomUUID();

            User user = UserFactory.build(
                    new Id(uuid.toString()),
                    name,
                    userName,
                    EncodeWrapper.Encoder(password),
                    Status.ACTIVE,
                    getDateFromString(dateOfBirth)
            );

            JSONObject obj = new JSONObject();
            obj.put("ID", user.ID().Id());
            obj.put("name", user.Name());
            obj.put("userLogin", user.UserLogin());
            obj.put("password", user.Password());
            obj.put("status", user.Status().toString());
            obj.put("crateAt", getDateFromDate(user.Date()));

            DocumentResult documentResult = this.elasticSearchClient
                    .prepareSearch(index)
                    .setType(type)
                    .set(obj.toJSONString(), user.ID().Id());

            stopConnection();

            if (documentResult.isSucceeded()) {
                return user;
            }

        } catch (java.io.IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public User loginUser(String userName, String password) {
        try {

            startConnection();

            SearchResult response = this.elasticSearchClient.
                    prepareSearch(index).
                    setType(type).
                    executeQuery(
                            LoginUserDSL.get(userName, password)
                    );


            if (!response.isSucceeded() || response.getTotal() <= 0) {
                stopConnection();
                return null;
            }

            SearchResult.Hit<JSONObject, Void> user = response.getFirstHit(JSONObject.class);
            stopConnection();
            return UserFactory.build(
                    new Id(user.id),
                    user.source.get("name").toString(),
                    user.source.get("userLogin").toString(),
                    user.source.get("password").toString(),
                    Status.valueOf(user.source.get("status").toString()),
                    getDateFromString(user.source.get("crateAt").toString())
            );
        } catch (Exception e) {
            Logger.printThrowable(e);
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public User getUserByToken(Token token) {
        try {
            startConnection();

            SearchResult response = this.elasticSearchClient.
                    prepareSearch(index_token).
                    setType(type_toke).
                    executeQuery(
                            UserByTokenDSL.get(token)
                    );

            if (!response.isSucceeded() || response.getTotal() <= 0) {
                return null;
            }

            SearchResult.Hit<JSONObject, Void> tokenJson = response.getFirstHit(JSONObject.class);

            JestResult responseUser = this.elasticSearchClient
                    .prepareSearch(index)
                    .setType(type)
                    .get(
                            tokenJson.source.get("userId").toString()
                    );

            if (!responseUser.isSucceeded()) {
                return null;
            }

            JSONObject userJson = responseUser.getSourceAsObject(JSONObject.class);
            stopConnection();
            return UserFactory.build(
                    new Id(userJson.get("ID").toString()),
                    userJson.get("name").toString(),
                    userJson.get("userLogin").toString(),
                    userJson.get("password").toString(),
                    Status.valueOf(userJson.get("status").toString()),
                    getDateFromString(userJson.get("crateAt").toString())
            );

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void finalize() throws Throwable {
        stopConnection();
        super.finalize();
    }
}
