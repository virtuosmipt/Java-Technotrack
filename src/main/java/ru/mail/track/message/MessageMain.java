package ru.mail.track.message;
import ru.mail.track.comands.CommandType;

import java.io.Serializable;
/**
 * Created by a.borodin on 22.11.2015.
 */
public class MessageMain implements Serializable {


    private String[] args;
    public MessageMain(){

    }
    public MessageMain(String[]args){
        this.args=args;

    }
    public String[] getArgs(){
        return args;
    }
}
