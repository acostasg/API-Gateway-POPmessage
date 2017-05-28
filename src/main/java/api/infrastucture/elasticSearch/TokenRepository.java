package api.infrastucture.elasticSearch;

import api.domain.entity.Token;
import api.domain.entity.User;
import api.domain.infrastructure.ConfigRepository;
import api.domain.infrastructure.UserRepository;
import api.infrastucture.cache.CacheTokenInterface;
import api.infrastucture.elasticSearch.queryDSL.EncodeWrapper;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.searchbox.core.DocumentResult;
import org.json.simple.JSONObject;

import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;


public class TokenRepository extends AbstractElasticSearchRepository implements api.domain.infrastructure.TokenRepository {

    private static final String index = "token_index";
    private static final String type = "token";

    private static final String SECRET = "secret";
    private static final String AUT_O = "autO";

    @Inject
    private UserRepository userRepository;

    @Inject
    private CacheTokenInterface cacheToken;

    @Inject
    private ConfigRepository configRepository;

    @Inject
    private EncodeWrapper encodeWrapper;

    private String SecretKeySpec(User user) {
        try {
            byte[] key = (user.userLogin() + getSecret() + user.password()).getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); // use only first 128 bit
            return new SecretKeySpec(key, "AES").toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return getSecret();
        }
    }

    private String getSecret() {
        return this.configRepository.get(SECRET);
    }

    @Override
    public Token generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.SecretKeySpec(user));
            String tokenString = JWT.create()
                    .withIssuer(getAutO())
                    .withExpiresAt(getDateExpiration())
                    .withSubject(user.ID().Id())
                    .sign(algorithm);

            Token token = new Token(
                    tokenString
            );


            JSONObject obj = new JSONObject();
            obj.put("hash", this.encodeWrapper.encode(token.hash()));
            obj.put("userId", user.ID().Id());
            obj.put("crateAt", getDateFromDate(new Date()));

            DocumentResult documentResult = this.elasticSearchClient
                    .prepareSearch(index)
                    .setType(type)
                    .set(obj.toJSONString(), user.ID().Id());

            if (documentResult.isSucceeded()) {
                this.cacheToken.setUser(user, token); //save in temp cache in memory
                return token;
            }

        } catch (UnsupportedEncodingException | JWTCreationException exception) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getAutO() {
        return this.configRepository.get(AUT_O);
    }

    @Override
    public Token validateToken(Token token) {
        try {
            User user;
            if (this.cacheToken.hasToken(token)) //check if user is in memory cache
                user = this.cacheToken.getUser(token);
            else
                user = userRepository.getUserByToken(token);

            DecodedJWT jwt = getDecodedJWT(token, user);

            if (!isValidTokenByUser(user, jwt)) {
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

    private DecodedJWT getDecodedJWT(Token token, User user) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(this.SecretKeySpec(user));
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(getAutO())
                .build();
        return verifier.verify(token.hash());
    }

    private boolean isValidTokenByUser(User user, DecodedJWT jwt) {
        return user != null || user.getId().equals(jwt.getSubject());
    }

    @Override
    public void deleteToken(User user, Token token) {
        try {
            if (this.cacheToken.hasToken(token)) //delete token in memory if exist
                this.cacheToken.deleteUser(token);

            this.elasticSearchClient
                    .prepareSearch(index)
                    .setType(type)
                    .del(user.ID().Id());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
