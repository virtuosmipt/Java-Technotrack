package ru.mail.track.message;

import ru.mail.track.comands.CommandType;

/**
 * Created by a.borodin on 10.11.2015.
 */
public class PassMessage extends Message {
    private String oldPass;
    private String newPass;

    public PassMessage() {
        setType(CommandType.USER_PASS);
    }
    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String login) {
        this.oldPass = login;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String pass) {
        this.newPass = pass;
    }
}