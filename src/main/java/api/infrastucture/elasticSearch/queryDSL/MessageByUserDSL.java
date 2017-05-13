package api.infrastucture.elasticSearch.queryDSL;

import api.domain.entity.User;

public class MessageByUserDSL {
    public static String get(User user) {
        return "{\n" +
                "    \"query\": {\n" +
                "            \"match\": { \"user.ID\": \"" + user.ID().Id() + "\"}\n" +
                "    }\n" +
                "}";
    }
}
