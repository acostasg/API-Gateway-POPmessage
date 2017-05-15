package api.domain.entity;

public class Location {

    private String lat;
    private String lon;

    public Location(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public String lat() {
        return this.lat;
    }

    public String lon() {
        return this.lon;
    }

    @Override
    public String toString() {
        return "{" +
                "\"lat\":\"" + lat + '\"' +
                ", \"lon\":\"" + lon + '\"' +
                "}";
    }
}