package api.infrastucture.elasticSearch.queryDSL.mappers;

import api.domain.entity.Location;

import java.util.regex.Pattern;

class GeoLocation {
    private String locationString;

    /**
     * /**
     * Geolocation parse from elasticsearch string
     *
     * @param locationString String
     */
    private GeoLocation(String locationString) {
        this.locationString = locationString;
    }

    static Location decode(String locationString) {
        return new GeoLocation(locationString).decode();
    }

    private Location decode() {
        String raw = this.locationString.replaceAll(Pattern.quote("{lon="), "");
        raw = raw.replaceAll(Pattern.quote("lat="), "");
        raw = raw.replaceAll(Pattern.quote("}"), "");
        String[] attr = raw.split(",");

        return new Location(attr[1], attr[0]);
    }
}
