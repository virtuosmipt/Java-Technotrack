package ru.mail.track.message;


import ru.mail.track.jdbc.DAO.Identified;

import java.util.concurrent.atomic.AtomicLong;

public class User implements Identified{
    private Long userId;
    private String name;
    private String pass;

    public User(){}
    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
        this.userId=null;
    }

    public Long getId() {
        return userId;
    }

    public void setId(Long id) {
        this.userId =id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + userId +
                ", name='" + name + '\'' +
                '}';
    }
}
