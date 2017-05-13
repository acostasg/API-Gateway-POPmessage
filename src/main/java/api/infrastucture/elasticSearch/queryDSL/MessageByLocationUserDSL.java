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
        return "{\n" +
                "\"from\" : " + from + ", \"size\" : " + limit + "," +
                "    \"query\": {\n" +
                "        \"bool\" : {\n" +
                must() +
                "            \"filter\" : {\n" +
                "                \"geo_distance\" : {\n" +
                "                    \"distance\" : \"" + distance + "km\",\n" +
                "                    \"location\" : {\n" +
                "                        \"lat\" : " + location.Lat() + ",\n" +
                "                        \"lon\" : " + location.Lon() + "\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";
    }

    private static String must() {
        return "      \"must\": [\n" +
                "        { \"match\": { \"status\": \"" + Status.ACTIVE + "\"   }}\n" +
                "      ],\n";
    }
}
