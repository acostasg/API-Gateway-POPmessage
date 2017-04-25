package api.domain.entity;

public class Vote {

    private Id messageID;
    private Id userID;
    private Type type;

    public Id getMessageID() {
        return messageID;
    }

    public Id getUserID() {
        return userID;
    }

    public Type getType() {
        return type;
    }

}