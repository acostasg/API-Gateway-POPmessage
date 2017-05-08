package api.domain.entity;

import java.util.List;

public class Message {

    private Id ID;
    private User user;
    private String text;
    private Location location;
    private List<Vote> votes;
    private Status status;

    public Message(
            Id ID,
            User user,
            String text,
            Location location,
            List<Vote> votes,
            Status status
    ) {
        this.ID = ID;
        this.user = user;
        this.text = text;
        this.location = location;
        this.votes = votes;
        this.status = status;
    }

    public Id ID() {
        return ID;
    }

    public User user() {
        return user;
    }

    public String Text() {
        return text;
    }

    public Location Location() {
        return location;
    }

    public List<Vote> Votes() {
        return votes;
    }

    public Status Status() {
        return status;
    }

    @Override
    public String toString() {
        return "{" +
                ID +
                ", \"user\":" + user.toStringByMessage() +
                ", \"text\":\"" + text + '\"' +
                ", \"location\":" + location +
                ", \"votes\":" + votes +
                ", \"status\":\"" + status + "\"" +
                "}";
    }
}
