package api.domain.entity;

public class Location {

    private String lat;
    private String lon;

    public Location(String lat, String lon){
        this.lat = lat;
        this.lon = lon;
    }

    public String Lat() {
        return this.lat;
    }

    public String Lon() {
        return this.lon;
    }

}