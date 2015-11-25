package ru.mail.track.net;

import ru.mail.track.message.Message;

import java.net.*;
import java.net.ProtocolException;

/**
 *
 */
public interface Protocol {

    Message decode(byte[] bytes)  throws java.net.ProtocolException;

    byte[] encode(Message msg) throws ProtocolException;

}
