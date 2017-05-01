package api.infrastucture.elasticSearch;

import api.domain.entity.Id;
import api.domain.entity.Status;
import api.domain.entity.Token;
import api.domain.entity.User;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import java.util.Date;

public class UserRepository extends AbstractElasticSearchRepository implements api.domain.infrastructure.UserRepository {

    private static final String index = "user_index";
    private static final String type = "user";

    private static final String index_token = "token_index";
    private static final String type_toke = "Token";


    @Override
    public User registerUser(String name, String dateOfBirth, String userName, String password) {
        return null;
    }

    @Override
    public User loginUser(String userName, String password) {
        return null;
    }

    @Override
    public User getUserByToken(Token token) {

        startConnection();

        SearchResponse response = this.elasticSearchClient.
                prepareSearch(index_token).
                setType(type_toke).
                executeQuery(
                        QueryBuilders.termQuery("token", token.hash())
                );

        if (response == null || response.getHits().totalHits() <= 0) {
            return null;
        }

        SearchHit searchHit = response.getHits().getAt(0);

        GetResponse responseUser = this.elasticSearchClient.get(
                searchHit.field("userId").getValue().toString(),
                index,
                type
        );

        stopConnection();

        if (responseUser == null || responseUser.isSourceEmpty()) {
            return null;
        }

        return new User(
                new Id(responseUser.getId()),
                responseUser.getField("name").getValue().toString(),
                responseUser.getField("userLogin").getValue().toString(),
                responseUser.getField("password").getValue().toString(),
                Status.ACTIVE,
                new Date(responseUser.getField("crateAt").getValue().toString())
        );
    }
}
