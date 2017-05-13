package api.infrastucture.elasticSearch.queryDSL;

import api.domain.entity.Location;

public class MessageByLocationUserDSL {

    static final int distance = 5;

    /**
     * Query string for search message for location
     *
     * @param location Location
     * @param from     int
     * @param limit    int
     * @return String
     */
    public static String get(Location location, int from, int limit) {
        return "{\n" +
                "\"from\" : " + from + ", \"size\" : " + limit + "," +
                "    \"query\": {\n" +
                "        \"bool\" : {\n" +
                "            \"must\" : {\n" +
                "                \"match_all\" : {}\n" +
                "            },\n" +
                "            \"filter\" : {\n" +
                "                \"geo_distance\" : {\n" +
                "                    \"distance\" : \""+distance+"km\",\n" +
                "                    \"location\" : {\n" +
                "                        \"lat\" : " + location.Lat() + ",\n" +
                "                        \"lon\" : " + location.Lat() + "\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";
    }
}
