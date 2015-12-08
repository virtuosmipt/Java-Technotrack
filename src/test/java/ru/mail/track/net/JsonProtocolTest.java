package ru.mail.track.net;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import ru.mail.track.comands.CommandType;
import ru.mail.track.message.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by a.borodin on 08.12.2015.
 */
public class JsonProtocolTest extends TestCase {

    public void testEncode() throws Exception {

    }

    public void testDecode() throws Exception {

    }
    private final Map<CommandType, Message> messages = new HashMap<>();
    private Protocol protocol = new JsonProtocol();

    @Before
    public void setup() {

        protocol = new JsonProtocol();
        String line = "\\login a a";
        String[] tokens = line.split(" ");
        Message login = new Message(tokens);
        login.setType(CommandType.USER_INFO);
        messages.put(CommandType.USER_INFO, login);


    }

    @Test
    public void testLogin() throws Exception {

        Message origin = messages.get(CommandType.USER_INFO);
        byte[] data = protocol.encode(origin);
        Message copy = protocol.decode(data);
        assertEquals(origin, copy);
    }

   /* @Test(expected = ProtocolException.class)
    public void testSend() throws Exception {
        Message origin = messages.get(CommandType.MSG_SEND);
        byte[] data = protocol.encode(origin);
        Message copy = protocol.decode(data);
        assertEquals(origin, copy);
    }*/
}