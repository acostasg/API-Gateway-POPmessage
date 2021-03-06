package api.domain.entity;

public class Vote {

    private Id messageID;
    private Id userID;
    private Type type;

    public Vote(Id messageID, Id userID, Type type) {
        this.messageID = messageID;
        this.userID = userID;
        this.type = type;
    }

    public Id messageID() {
        return messageID;
    }

    public Id userID() {
        return userID;
    }

    public Type type() {
        return type;
    }

    @Override
    public String toString() {
        return "{" +
                "\"messageID\": " + messageID.toStringVote() + "" +
                ", \"userID\":" + userID.toStringVote() + "" +
                ", \"type\":\"" + type + "\"" +
                "}";
    }
}