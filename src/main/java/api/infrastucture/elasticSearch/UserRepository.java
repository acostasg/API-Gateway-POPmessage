package api.infrastucture.elasticSearch;

import api.domain.entity.Id;
import api.domain.entity.Status;
import api.domain.entity.Token;
import api.domain.entity.User;
import api.domain.factory.MessageFactory;
import api.domain.factory.UserFactory;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class UserRepository extends AbstractElasticSearchRepository implements api.domain.infrastructure.UserRepository {

    private static final String index = "user_index";
    private static final String type = "user";

    private static final String index_token = "token_index";
    private static final String type_toke = "Token";


    @Override
    public User registerUser(String name, String dateOfBirth, String userName, String password) {
        try {
            startConnection();

            UUID uuid = UUID.randomUUID();

            User user = UserFactory.build(
                    new Id( uuid.toString()),
                    name,
                    userName,
                    password, //TODO encrypted to secret key
                    Status.ACTIVE,
                    getDateFromString(dateOfBirth)
            );


            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("ID", user.ID().Id())
                    .field("name", user.Name())
                    .field("userLogin", user.UserLogin())
                    .field("password", user.Password())
                    .field("status", user.Status())
                    .field("crateAt", user.Date())
                    .endObject();

            IndexResponse response = this.elasticSearchClient.set(
                    uuid.toString(),
                    index,
                    type,
                    builder
            );

            System.out.println(response.toString());

            return user;
        } catch (java.io.IOException exception){
            return null;
        }

    }

    private Date getDateFromString(String dateOfBirth) {
        DateFormat format = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
        Date date;
        try {
            date = format.parse(dateOfBirth);
        } catch (ParseException e) {
           date = new Date();
        }
        return date;
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
