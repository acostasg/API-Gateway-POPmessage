package api.infrastucture.elasticSearch;

import api.domain.entity.Id;
import api.domain.entity.Status;
import api.domain.entity.Token;
import api.domain.entity.User;
import api.domain.exceptions.UserInUse;
import api.domain.factory.UserFactory;
import api.infrastucture.cache.CacheTokenInterface;
import api.infrastucture.elasticSearch.queryDSL.EncodeWrapper;
import api.infrastucture.elasticSearch.queryDSL.LoginUserDSL;
import api.infrastucture.elasticSearch.queryDSL.UserByEmailDSL;
import api.infrastucture.elasticSearch.queryDSL.UserByTokenDSL;
import api.infrastucture.elasticSearch.queryDSL.mappers.UserMapper;
import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.SearchResult;
import org.glassfish.hk2.utilities.reflection.Logger;
import org.json.simple.JSONObject;

import javax.inject.Inject;
import java.util.UUID;

public class UserRepository extends AbstractElasticSearchRepository implements api.domain.infrastructure.UserRepository {

    private static final String index = "user_index";
    private static final String type = "user";

    private static final String index_token = "token_index";
    private static final String type_toke = "token";

    private final UserMapper userMapper = new UserMapper();

    @Inject
    private CacheTokenInterface cacheToken;

    @Override
    public User registerUser(String name, String dateOfBirth, String userName, String password) throws UserInUse {
        try {

            UUID uuid = UUID.randomUUID();

            User user = UserFactory.build(
                    new Id(uuid.toString()),
                    name,
                    userName,
                    EncodeWrapper.Encoder(password),
                    Status.ACTIVE,
                    getDateFromString(dateOfBirth)
            );

            SearchResult responseUser = this.elasticSearchClient
                    .prepareSearch(index)
                    .setType(type)
                    .executeQuery(UserByEmailDSL.get(user));

            if (!responseUser.isSucceeded() || responseUser.getTotal() > 0) {
                throw new UserInUse(
                        "User Login is use for other user"
                );
            }

            DocumentResult documentResult = this.elasticSearchClient
                    .prepareSearch(index)
                    .setType(type)
                    .set(
                            this.userMapper.encoderUser(user),
                            user.ID().Id()
                    );


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
            SearchResult response = this.elasticSearchClient.
                    prepareSearch(index).
                    setType(type).
                    executeQuery(
                            LoginUserDSL.get(userName, password)
                    );


            if (!response.isSucceeded() || response.getTotal() <= 0) {
                return null;
            }

            SearchResult.Hit<JSONObject, Void> user = response.getFirstHit(JSONObject.class);
            return this.userMapper.builderUserSearch(
                    user
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

            if (this.cacheToken.hasToken(token))
                return this.cacheToken.getUser(token);


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
            User user = this.userMapper.builderUser(
                    userJson
            );

            if (!this.cacheToken.hasToken(token))
                this.cacheToken.setUser(user, token);

            return user;

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
