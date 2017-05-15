package api.domain.entity;

public class Token {

    private String hash;

    private String userId;

    private String crateAt;

    public Token(String hash) {
        this.hash = hash;
    }

    public Token(String hash, String userId, String crateAt) {
        this.hash = hash;
        this.userId = userId;
        this.crateAt = crateAt;
    }

    public String hash() {
        return this.hash;
    }

    public boolean isEmpty() {
        return this.hash.isEmpty();
    }

    public boolean isHashEqual(String $hash) {
        return this.hash.equals($hash);
    }

    public String userId() {
        return userId;
    }

    public String crateAt() {
        return crateAt;
    }

    @Override
    public String toString() {
        return "{" +
                "\"hash\":\"" + hash + '\"' +
                "}";
    }
}
