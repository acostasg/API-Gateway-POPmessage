package api.infrastucture.elasticSearch.queryDSL;

import api.domain.entity.User;

public class UserByEmailDSL {
    /**
     * Return query dsl for get user for your token
     *
     * @param user User
     * @return String
     */
    public static String get(User user) {
        return "{" +
                "   \"query\": {" +
                "      \"constant_score\": {" +
                "         \"filter\": {" +
                "            \"term\": {" +
                "               \"userLogin\": \"" + user.userLogin() + "\"" +
                "            }" +
                "         }" +
                "      }" +
                "   }" +
                "}";
    }
}
