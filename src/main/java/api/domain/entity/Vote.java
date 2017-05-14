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

    public Id MessageID() {
        return messageID;
    }

    public Id UserID() {
        return userID;
    }

    public Type Type() {
        return type;
    }

    @Override
    public String toString() {
        return "{" +
                "\"messageID\": { "+ messageID + "}" +
                ", \"userID\":{" + userID + "}" +
                ", \"type\":\"" + type + "\"" +
                "}";
    }
}