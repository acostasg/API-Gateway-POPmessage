package api.domain.command.request;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class LoginUserRequest implements Request {

    private String userName;
    private String password;

    public LoginUserRequest(
            String userName,
            String password
    ){
        this.userName = userName;
        this.password = password;
    }

    public String UserName() {
        return userName;
    }

    public String Password() {
        return password;
    }

}
