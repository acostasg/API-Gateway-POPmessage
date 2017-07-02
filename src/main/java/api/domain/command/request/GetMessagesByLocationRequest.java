package api.domain.command.request;

import api.domain.entity.Location;

public class GetMessagesByLocationRequest implements Request {

    private Location location;
    private int last;

    public GetMessagesByLocationRequest(
            String lat,
            String lon,
            int last
    ) {
        this.last = last;
        this.location = new Location(lat, lon);
    }

    public Location location() {
        return location;
    }

    public int last() {
        return this.last;
    }
}
