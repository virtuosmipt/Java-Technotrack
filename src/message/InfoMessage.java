package message;

import comands.CommandType;
import session.Session;

/**
 * Created by a.borodin on 09.11.2015.
 */
public class InfoMessage extends Message {

    private String stringInfo;

    public InfoMessage(){
        setType(CommandType.USER_INFO);
    }
    public void setStringInfo(String str){
        this.stringInfo=str;
    }
    public String getStringInfo(){
        return stringInfo;
    }
}
