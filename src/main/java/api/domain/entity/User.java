package api.domain.entity;

public class User {

    private Id ID;
    private String name;
    private String userLogin;
    private String password;
    private Status status;

    public User(Id ID, String name, String userLogin, String password, Status status )
    {
        this.ID = ID;
        this.name = name;
        this.userLogin = userLogin;
        this.password = password;
        this.status = status;

    }

    public Id ID() {
        return ID;
    }

    public String Name() {
        return name;
    }

    public String UserLogin() {
        return userLogin;
    }

    public String Password() {
        return password;
    }

    public Status Status() {
        return status;
    }

    @Override
    public String toString() {
        return "{\"User\":{"
                + ID +
                ", \"name\":\"" + name + '\"' +
                ", \"userLogin\":\"" + userLogin + '\"' +
                ", \"status\":\"" + status + '\"' +
                "}}";
    }
}