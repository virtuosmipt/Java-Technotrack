package message;

import comands.CommandType;

/**
 * Created by a.borodin on 09.11.2015.
 */
public class ErrorLoginMessage extends Message {
    private String errorLogin;

    public ErrorLoginMessage() {
        setType(CommandType.ERROR_LOGIN);
    }
    public ErrorLoginMessage(String str){
        this.errorLogin=str;
    }
    public void setErrorMessage(String str){
        this.errorLogin=str;
    }
    public String getErrorLogin(){
        return errorLogin;
    }
}
