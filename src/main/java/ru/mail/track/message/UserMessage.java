package ru.mail.track.message;

import ru.mail.track.comands.CommandType;

/**
 * Created by a.borodin on 10.11.2015.
 */
public class UserMessage extends Message {
    private String name;
    public UserMessage() {
        setType(CommandType.USER_NAME);
    }
    public String getLogin() {
        return name;
    }

    public void setLogin(String login) {
        this.name = login;
    }
}
