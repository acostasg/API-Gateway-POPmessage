package api.infrastucture.elasticSearch.queryDSL;

import api.domain.entity.Token;


public class UserByTokenDSL {
    public static String get(Token token) {

        return "{\n" +
                "  \"query\": {\n" +
                "    \"match\" : { \"hash\" : \"" + EncodeWrapper.Encoder(token.hash()) + "\" } \n" +
                "  }\n" +
                "}";
    }
}
