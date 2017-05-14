package api.infrastucture.elasticSearch.queryDSL;


import api.domain.entity.Vote;

public class AddVoteToMessageDSL {
    /**
     * @param vote Vote
     * @return
     */
    public static String get(Vote vote) {
        return "{" +
                " \"script\": {" +
                "  \"params\" : {" +
                "  \"vote\" :{" +
                "    \"messageID\":\"" + vote.MessageID().Id() + "\"," +
                "    \"userID\":\"" + vote.UserID().Id() + "\"," +
                "    \"type\":\"" + vote.Type() + "\"" +
                "  }" +
                "}, \"inline\":" +
                "\"if (ctx._source.containsKey(\\\"votes\\\")) {ctx._source.votes.add(params.vote)} else {ctx._source.votes = [params.vote]}\" }" +
                "}";
    }
}
