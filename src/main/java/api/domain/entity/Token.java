package api.domain.entity;

public class Token {

    private String hash;

    public Token(String hash) {
        this.hash = hash;
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

    @Override
    public String toString() {
        return "{" +
                "\"hash\":\"" + hash + '\"' +
                "}";
    }
}
