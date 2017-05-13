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
        return "{\n" +
                "    \"query\": {\n" +
                "        \"bool\": {\n" +
                "            \"must\": [\n" +
                "            { \"match\": { \"userLogin\": \"" + userName + "\"}},\n" +
                "            { \"match\": { \"password\": \"" + EncodeWrapper.Encoder(password) + "\"}}\n" +
                "            ]\n" +
                "        }\n" +
                "    }\n" +
                "}";
    }
}
