package api.infrastucture.elasticSearch.queryDSL;


import api.domain.entity.Token;

public class UserByTokenDSL {
    public static String get(Token token) {
        return "{\n" +
                "  \"query\": {\n" +
                "    \"term\" : { \"token\" : \"" + token.hash() + "\" } \n" +
                "  }\n" +
                "}";
    }
}
