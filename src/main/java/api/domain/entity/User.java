package api.domain.entity;

import api.domain.service.FormatDataService;

import java.util.Date;

public class User {

    private Id ID;
    private String name;
    private String userLogin;
    private String password;
    private Status status;
    private Date date;

    public User(
            Id ID,
            String name,
            String userLogin,
            String password,
            Status status
    ) {
        this(
                ID,
                name,
                userLogin,
                password,
                status,
                new Date()
        );
    }

    public User(
            Id ID,
            String name,
            String userLogin,
            String password,
            Status status,
            Date date
    ) {
        this.ID = ID;
        this.name = name;
        this.userLogin = userLogin;
        this.password = password;
        this.status = status;
        this.date = date;
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

    public Date Date() {
        return date;
    }

    @Override
    public String toString() {
        return "{\"User\":{"
                + ID +
                ", \"name\":\"" + name + '\"' +
                ", \"userLogin\":\"" + userLogin + '\"' +
                ", \"status\":\"" + status + '\"' +
                ", \"date\":\"" + FormatDataService.DataFormat(this.date) + '\"' +
                "}}";
    }
}