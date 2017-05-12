package api.infrastucture.elasticSearch.queryDSL;


public class LoginUserDSL {
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
