package api.domain.entity;

public class Id {

    private String id;

    public Id() {
    }

    public Id(String id) {
        this.id = id;
    }

    public String Id() {
        return this.id;
    }

    @Override
    public String toString() {
        return "\"Id\":{\"id\":\"" + id + "\"}";
    }

    String toStringVote() {
        return "{\"id\":\"" + id + "\"}";
    }
}