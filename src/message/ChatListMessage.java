package message;

import comands.CommandType;

import java.util.AbstractCollection;
import java.util.ArrayList;

/**
 * Created by a.borodin on 10.11.2015.
 */
public class ChatListMessage extends Message {
    public ChatListMessage() {
        setType(CommandType.CHAT_LIST);
    }
    public  ArrayList<Long> chatsId = new ArrayList<>();



}
