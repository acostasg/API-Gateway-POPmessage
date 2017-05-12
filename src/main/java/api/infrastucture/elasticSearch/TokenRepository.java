package api.infrastucture.elasticSearch;

import api.domain.entity.Token;
import api.domain.entity.User;
import api.domain.infrastructure.UserRepository;
import api.infrastucture.elasticSearch.queryDSL.EncodeWrapper;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.searchbox.core.DocumentResult;
import org.json.simple.JSONObject;

import javax.inject.Inject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;


public class TokenRepository extends AbstractElasticSearchRepository implements api.domain.infrastructure.TokenRepository {

    private static final String index = "token_index";
    private static final String type = "token";

    private static final String secret = "G2fBYWd9W43z63IaAro9J9QWOi2t03zw";
    private static final String autO = "k9i4E1t8tI";

    @Inject
    private UserRepository userRepository;

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


            JSONObject obj = new JSONObject();
            obj.put("hash", EncodeWrapper.Encoder(token.hash()));
            obj.put("userId", user.ID().Id());
            obj.put("crateAt", getDateFromDate(new Date()));


            startConnection();
            DocumentResult documentResult = this.elasticSearchClient
                    .prepareSearch(index)
                    .setType(type)
                    .set(obj.toJSONString(), user.ID().Id());
            stopConnection();

            if (documentResult.isSucceeded()) {
                return token;
            }

        } catch (UnsupportedEncodingException | JWTCreationException exception) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Token validateToken(Token token) {
        try {

            User user = userRepository.getUserByToken(token);

            DecodedJWT jwt = getDecodedJWT(token);

            if (isValidTokenByUser(user, jwt)) {
                return null;
            }

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

    private DecodedJWT getDecodedJWT(Token token) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(autO)
                .build();
        return verifier.verify(token.hash());
    }

    private boolean isValidTokenByUser(User user, DecodedJWT jwt) {
        return user != null || !user.ID().Id().equals(jwt.getSubject());
    }

    @Override
    public void deleteToken(User user, Token token) {
        try {
            startConnection();
            this.elasticSearchClient
                    .prepareSearch(index)
                    .setType(type)
                    .del(user.ID().Id());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stopConnection();
    }
}
