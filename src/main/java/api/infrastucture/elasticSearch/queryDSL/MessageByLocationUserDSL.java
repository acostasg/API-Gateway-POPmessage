package api.infrastucture.elasticSearch.queryDSL;

import api.domain.entity.Location;
import api.domain.entity.Status;

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
        return "{" +
                "\"from\" : " + from + ", \"size\" : " + limit + "," + PartialSortDSL.sort() +
                "    \"query\": {" +
                "        \"bool\" : {" +
                must() +
                "            \"filter\" : {" +
                "                \"geo_distance\" : {" +
                "                    \"distance\" : \"" + distance + "km\"," +
                "                    \"location\" : {" +
                "                        \"lat\" : " + location.lat() + "," +
                "                        \"lon\" : " + location.lon() + "" +
                "                    }" +
                "                }" +
                "            }" +
                "        }" +
                "    }" +
                "}";
    }

    /**
     *
     * @return String
     */
    private static String must() {
        return "      \"must\": [" +
                "        { \"match\": { \"status\": \"" + Status.ACTIVE + "\"   }}" +
                "      ],";
    }
}
