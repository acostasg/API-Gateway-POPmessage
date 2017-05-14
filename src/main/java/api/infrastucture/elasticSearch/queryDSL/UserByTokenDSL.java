package api.infrastucture.elasticSearch.queryDSL;

import api.domain.entity.Token;

public class UserByTokenDSL {
    /**
     * Return query dsl for get user for your token
     *
     * @param token Token
     * @return String
     */
    public static String get(Token token) {

        return "{" +
                "  \"query\": {" +
                "    \"match\" : { \"hash\" : \"" + EncodeWrapper.Encoder(token.hash()) + "\" }" +
                "  }" +
                "}";
    }
}
