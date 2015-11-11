package message;

import comands.CommandType;

import java.util.ArrayList;

/**
 * Created by a.borodin on 11.11.2015.
 */
public class ChatFindMessage extends Message {
    private Long idChat;
    private String stringFind;
    public  ArrayList<String> findingStringList = new ArrayList<>();

    public ChatFindMessage() {
        setType(CommandType.CHAT_FIND);
    }

    public void setIdChat(Long id){
        this.idChat=id;
    }
    public Long getIdChat(){
        return this.idChat;
    }
    public void setStringFind(String str){
        this.stringFind=str;
    }
    public String getStringFind(){
        return stringFind;
    }
}
