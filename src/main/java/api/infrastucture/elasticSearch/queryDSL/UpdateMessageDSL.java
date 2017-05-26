package api.infrastucture.elasticSearch.queryDSL;

import api.domain.entity.Message;

public class UpdateMessageDSL {
    /**
     * partial update for message
     *
     * @param message {@link Message}
     * @return String
     */
    public static String get(Message message) {
        return "{\n" +
                "   \"doc\" : {" +
                "      \"text\": \"" + message.Text() + "\" " +
                "   }\n" +
                "}";
    }
}
