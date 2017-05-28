package api.infrastucture.elasticSearch.queryDSL;

import api.domain.entity.Token;

public class UserByTokenDSL {
    /**
     * Return query dsl for get user for your token
     *
     * @param token Token
     * @return String
     */
    public static String get(Token token, EncodeWrapper encodeWrapper) {
        return "{" +
                "   \"query\": {" +
                "      \"constant_score\": {" +
                "         \"filter\": {" +
                "            \"term\": {" +
                "               \"hash\": \"" + encodeWrapper.encode(token.hash()) + "\"" +
                "            }" +
                "         }" +
                "      }" +
                "   }" +
                "}";
    }
}
