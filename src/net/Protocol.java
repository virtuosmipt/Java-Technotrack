package net;

import message.Message;

/**
 *
 */
public interface Protocol {

    Message decode(byte[] bytes);

    byte[] encode(Message msg);

}
