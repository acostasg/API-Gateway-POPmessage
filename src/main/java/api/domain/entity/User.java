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

    public User() {  // no-arg constructor
    }

    public User(
            Id ID,
            String name
    ) {
        this(
                ID,
                name,
                null,
                null,
                null,
                new Date()
        );
    }

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

    public String getId() {
        return ID.Id();
    }

    public String Name() {
        return name;
    }

    public String userLogin() {
        return userLogin;
    }

    public String password() {
        return password;
    }

    public Status status() {
        return status;
    }

    public Date date() {
        return date;
    }

    @Override
    public String toString() {
        return "{"
                + ID +
                ", \"name\":\"" + name + '\"' +
                ", \"userLogin\":\"" + userLogin + '\"' +
                ", \"status\":\"" + status + '\"' +
                ", \"date\":\"" + FormatDataService.DataFormat(this.date) + '\"' +
                "}";
    }

    String toStringByMessage() {
        return "{"
                + ID +
                ", \"name\":\"" + name + '\"'
                + "}";
    }
}