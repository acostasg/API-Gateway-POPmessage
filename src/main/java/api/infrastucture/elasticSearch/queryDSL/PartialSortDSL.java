package api.infrastucture.elasticSearch.queryDSL;

class PartialSortDSL {
    /**
     * Order by last message user publish
     *
     * @return String
     */
    static String sort() {
        return "    \"sort\" : [\n" +
                " { \"createAd\" : {\"order\" : \"asc\"}}," + //order for createAd, first last create
                " { \"sort\" : {\"order\" : \"desc\"}}," + //order for likes
                " { \"_score\" : {\"order\" : \"asc\"}}" + //_score for algorithm scope for query
                "    ],";
    }
}
