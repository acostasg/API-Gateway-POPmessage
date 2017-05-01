package api.domain.entity;

import java.util.List;

public class Message {

    private Id ID;
    private Id userID;
    private String text;
    private Location location;
    private List<Vote> votes;
    private Status status;

    public Message(
            Id ID,
            Id userID,
            String text,
            Location location,
            List<Vote> votes,
            Status status
    ) {
        this.ID = ID;
        this.userID = userID;
        this.text = text;
        this.location = location;
        this.votes = votes;
        this.status = status;
    }

    public Id ID() {
        return ID;
    }

    public Id UserID() {
        return userID;
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
        return "{ \"Message\":{" +
                ID +
                "," + userID +
                ", \"text\":\"" + text + '\"' +
                ", \"location\":" + location +
                ", \"votes\":" + votes +
                ", \"status\":\"" + status + "\"" +
                "}}";
    }
}
