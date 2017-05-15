package api.infrastucture.elasticSearch.queryDSL;


import api.domain.entity.Type;
import api.domain.entity.Vote;

public class AddVoteToMessageDSL {
    /**
     * @param vote Vote
     * @return String
     */
    public static String get(Vote vote) {
        return "{" +
                " \"script\": {" +
                "  \"params\" : {" +
                "  \"vote\" :{" +
                "    \"messageID\":\"" + vote.messageID().Id() + "\"," +
                "    \"userID\":\"" + vote.userID().Id() + "\"," +
                "    \"type\":\"" + vote.type() + "\"" +
                "  }" +
                "}, \"inline\":" +
                "\" " + actionSort(vote) + "; if (ctx._source.containsKey(\\\"votes\\\")) {ctx._source.votes.add(params.vote)} else {ctx._source.votes = [params.vote]}\" }" +
                "}";
    }


    /**
     * @param vote Vote
     * @return String
     */
    private static String actionSort(Vote vote) {
        if (vote.type() == Type.POSITIVE) {
            return "ctx._source.sort+=1";
        } else {
            return "ctx._source.sort-=1";
        }
    }
}
