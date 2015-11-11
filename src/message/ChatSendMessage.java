package message;

import comands.CommandType;

import java.util.ArrayList;

/**
 * Created by a.borodin on 11.11.2015.
 */
public class ChatSendMessage extends  Message {
    public  ArrayList<Long> chatsId = new ArrayList<>();
    private String messages;

    public ChatSendMessage() {
        setType(CommandType.CHAT_SEND);
    }
    public String getMessages(){
        return this.messages;
    }
    public void setMessages(String str){
        this.messages=str;
    }


}
