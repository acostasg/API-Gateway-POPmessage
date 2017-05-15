package api.infrastucture.elasticSearch.queryDSL;

class PartialSortDSL {
    /**
     * Order by last message user publish
     *
     * @return String
     */
    static String sort() {
        return "    \"sort\" : [\n" +
                " { \"sort\" : {\"order\" : \"desc\"}}," + //order for likes
                " { \"createAd\" : {\"order\" : \"asc\"}}," + //order for createAd, first last create
                " { \"_score\" : {\"order\" : \"asc\"}}" + //_score for algorithm scope for query
                "    ],";
    }
}
