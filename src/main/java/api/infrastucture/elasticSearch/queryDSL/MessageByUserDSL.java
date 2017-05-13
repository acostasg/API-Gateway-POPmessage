package api.infrastucture.elasticSearch.queryDSL;

import api.domain.entity.User;

public class MessageByUserDSL {
    /**
     * Query string for search message for user
     *
     * @param user  User
     * @param from  int
     * @param limit int
     * @return String
     */
    public static String get(User user, int from, int limit) { //TODO order by date
        return "{\n" +
                "\"from\" : " + from + ", \"size\" : " + limit + "," + MessageByUserDSL.sort() +
                query(user) +
                "}";
    }

    private static String query(User user) {
        return "  \"query\": {\n" +
                "    \"bool\": {\n" +
                "      \"must\": [\n" +
                "        { \"match\": { \"user.ID\":  \"" + user.ID().Id() + "\" }},\n" +
                "        { \"match\": { \"status\": \"ACTIVE\"   }}\n" +
                "      ]\n" +
                "    }\n" +
                "  }";
    }

    /**
     * Order by last message user publish
     *
     * @return String
     */
    private static String sort() {
        return "    \"sort\" : [\n" +
                " { \"_score\" : {\"order\" : \"asc\"}}" +
                "    ],";
    }
}
