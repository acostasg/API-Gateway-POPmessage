package api.domain.command.request;

public class RegisterUserRequest implements Request {

    private String name;
    private String dateOfBirth;
    private String userName;
    private String password;
    private boolean privacyPolicy;

    public RegisterUserRequest(
            String name,
            String dateOfBirth,
            String userName,
            String password,
            boolean privacyPolicy
    ) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.userName = userName;
        this.password = password;
        this.privacyPolicy = privacyPolicy;
    }

    public String Name() {
        return name;
    }

    public String DateOfBirth() {
        return dateOfBirth;
    }

    public String UserName() {
        return userName;
    }

    public String Password() {
        return password;
    }

    public boolean PrivacyPolicy() {
        return privacyPolicy;
    }
}
