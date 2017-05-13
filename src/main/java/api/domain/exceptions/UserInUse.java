package api.domain.exceptions;

public class UserInUse extends Exception {
    public UserInUse(String message) {
        super(message);
    }
}