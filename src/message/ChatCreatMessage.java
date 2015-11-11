package message;

import comands.CommandType;

import java.util.ArrayList;

/**
 * Created by a.borodin on 10.11.2015.
 */
public class ChatCreatMessage extends Message {
    public ArrayList<Long> userIdList = new ArrayList<>();
    public ChatCreatMessage() {
        setType(CommandType.CHAT_CREAT);
    }

}
