package api.infrastucture.elasticSearch.queryDSL;


import api.domain.entity.Status;

public class LoginUserDSL {
    /**
     * Return query dsl for check login user
     *
     * @param userName String
     * @param password String
     * @return String
     */
    public static String get(String userName, String password, EncodeWrapper encodeWrapper) {
        return "{\n" +
                "   \"query\": {" +
                "      \"constant_score\": {" +
                "         \"filter\": {" +
                "            \"bool\": {" +
                "               \"must\": [" +
                "                  {\n" +
                "                     \"term\": {" +
                "                        \"userLogin\": \"" + userName + "\"" +
                "                     }" +
                "                  }," +
                "                  {" +
                "                     \"term\": {" +
                "                        \"password\": \"" + encodeWrapper.encode(password) + "\"" +
                "                     }" +
                "                  }," +
                "                  {" +
                "                     \"term\": {" +
                "                        \"status\": \"" + Status.ACTIVE + "\"" +
                "                     }" +
                "                  }" +
                "               ]" +
                "            }" +
                "         }" +
                "      }" +
                "   }" +
                "}";
    }
}
