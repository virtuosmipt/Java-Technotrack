package ru.mail.track.net;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ru.mail.track.comands.CommandType;
import ru.mail.track.message.LoginMessage;
import ru.mail.track.message.Message;
import ru.mail.track.message.SendMessage;
import ru.mail.track.net.JsonProtocol;
import ru.mail.track.net.ProtocolException;

import static org.junit.Assert.*;

/**
 *
 */
public class JsonProtocolTest {

    private final Map<CommandType, Message> messages = new HashMap<>();
    private Protocol protocol;

    @Before
    public void setup() {

        protocol = new JsonProtocol();
        String[] arg={"\\login","Virtuos","111"};
        Message login = new Message();

        messages.put(CommandType.USER_LOGIN, login);

        SendMessage send = new SendMessage();
        send.setChatId(1L);
        send.setMessage("Hello world!");
        messages.put(CommandType.MSG_SEND, send);

    }

    @Test
    public void testLogin() throws Exception {

        Message origin = messages.get(CommandType.USER_LOGIN);
        byte[] data = protocol.encode(origin);
        Message copy = protocol.decode(data);
        assertEquals(origin, copy);
    }


}