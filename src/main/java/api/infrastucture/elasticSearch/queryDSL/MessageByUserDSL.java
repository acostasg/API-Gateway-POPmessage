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
        return "{" +
                "\"from\" : " + from + ", \"size\" : " + limit + "," + MessageByUserDSL.sort() +
                query(user) +
                "}";
    }

    private static String query(User user) {
        return "  \"query\": {" +
                "    \"bool\": {" +
                "      \"must\": [" +
                "        { \"match\": { \"user.ID\":  \"" + user.ID().Id() + "\" }}," +
                "        { \"match\": { \"status\": \"ACTIVE\"   }}" +
                "      ]" +
                "    }" +
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
