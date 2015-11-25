package ru.mail.track.message;

import ru.mail.track.comands.CommandType;

import java.util.ArrayList;

/**
 * Created by a.borodin on 10.11.2015.
 */
public class ChatCreatMessage extends Message {
    public ArrayList<Long> userIdList = new ArrayList<Long>();
    public ChatCreatMessage() {
        setType(CommandType.CHAT_CREAT);
    }

}
