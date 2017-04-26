package api.domain.command.request;

import api.domain.entity.Location;

public class GetMessagesByLocationRequest implements Request {

    private Location location;

    public GetMessagesByLocationRequest(
            String lat,
            String lon
    ) {
        this.location = new Location(lat, lon);
    }

    public Location location() {
        return location;
    }
}
