package api.infrastucture.elasticSearch.queryDSL;


public class LoginUserDSL {
    /**
     * Return query dsl for check login user
     *
     * @param userName String
     * @param password String
     * @return
     */
    public static String get(String userName, String password) {
        return "{" +
                "    \"query\": {" +
                "        \"bool\": {" +
                "            \"must\": [" +
                "            { \"match\": { \"userLogin\": \"" + userName + "\"}}," +
                "            { \"match\": { \"password\": \"" + EncodeWrapper.Encoder(password) + "\"}}" +
                "            ]" +
                "        }" +
                "    }" +
                "}";
    }
}
