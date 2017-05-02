package api.infrastucture.elasticSearch;

import api.domain.entity.Token;
import api.domain.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class TokenRepository extends AbstractElasticSearchRepository implements api.domain.infrastructure.TokenRepository {

    private static final String index = "token_index";
    private static final String type = "token";

    private static final String secret = "G2fBYWd9W43z63IaAro9J9QWOi2t03zw";
    private static final String autO = "k9i4E1t8tI";

    @Override
    public Token generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String tokenString = JWT.create()
                    .withIssuer(autO)
                    .withExpiresAt(getDateExpiration())
                    .withSubject(user.ID().Id())
                    .sign(algorithm);

            Token token = new Token(
                    tokenString
            );

            try {
                XContentBuilder xContentBuilder = jsonBuilder()
                        .startObject()
                        .field("token", tokenString)
                        .field("crateAt", new Date())
                        .field("userId", user.ID().Id())
                        .endObject();

                startConnection();
                this.elasticSearchClient.set(token.hash(), index, type, xContentBuilder);
                stopConnection();

            } catch (java.io.IOException exception) {
                return null;
            }

            //TODO save to elasticsearch

            return token;

        } catch (UnsupportedEncodingException | JWTCreationException exception) {
            return null;
        }
    }

    @Override
    public Token validateToken(Token token) {
        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(autO)
                    .build();
            DecodedJWT jwt = verifier.verify(token.hash());

            Date date = new Date();

            if (date.after(jwt.getExpiresAt())) {
                return null;
            }

            return new Token(
                    jwt.getToken()
            );
        } catch (UnsupportedEncodingException | JWTVerificationException exception) {
            return null;
        }
    }

    @Override
    public void deleteToken(User user, Token token) {
        //TODO delete elasticsearch
    }
}
