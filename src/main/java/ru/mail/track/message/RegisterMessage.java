package ru.mail.track.message;
import ru.mail.track.comands.CommandType;

/**
 * Created by a.borodin on 09.11.2015.
 */
public class RegisterMessage extends Message {

    private String[] args;
    public RegisterMessage() {

    }
    public RegisterMessage(String[] args) {
        setType(CommandType.USER_REGISTER);
        this.args=args;
    }


    public String[] getArgs(){return this.args;}

    public void setArgs(String[] args){
       this.args=args;
   }

}
