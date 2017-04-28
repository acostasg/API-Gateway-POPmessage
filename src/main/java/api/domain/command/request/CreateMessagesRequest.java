package api.domain.command.request;

import api.domain.entity.Location;
import api.domain.entity.User;

public class CreateMessagesRequest implements Request {

    private String text;
    private User user;
    private Location location;

    public CreateMessagesRequest(
            String text,
            User user,
            String lat,
            String lon
    ) {
        this.text = text;
        this.user = user;
        this.location = new Location(lat, lon);
    }

    public Location Location() {
        return location;
    }

    public String Text() {
        return text;
    }

    public User User() {
        return user;
    }
}
