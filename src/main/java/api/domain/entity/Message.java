package api.domain.entity;

import java.util.Date;
import java.util.List;

public class Message {

    private Id ID;
    private User user;
    private String text;
    private Location location;
    private List<Vote> votes;
    private Status status;
    private Date createAt;

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
        this.createAt = new Date();
    }

    public Message(
            Id ID,
            User user,
            String text,
            Location location,
            List<Vote> votes,
            Status status,
            Date createAd
    ) {
        this.ID = ID;
        this.user = user;
        this.text = text;
        this.location = location;
        this.votes = votes;
        this.status = status;
        this.createAt = createAd;
    }

    public Id ID() {
        return ID;
    }

    public User User() {
        return user;
    }

    public String userId() {
        return this.user.getId();
    }

    public String Text() {
        return text;
    }

    public Location Location() {
        return location;
    }

    public List<Vote> votes() {
        return votes;
    }

    public Status status() {
        return status;
    }

    public Date createAt() {
        return this.createAt;
    }

    public void delete() {
        this.status = Status.DELETED;
    }

    public void addVote(Vote vote) {
        this.votes.add(vote);
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
