package message;
import comands.CommandType;

/**
 * Created by a.borodin on 09.11.2015.
 */
public class RegisterMessage extends Message {
    private String login;
    private String pass;
    public RegisterMessage() {
        setType(CommandType.USER_REGISTER);
    }
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
