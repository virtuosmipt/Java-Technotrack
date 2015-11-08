package net;

import message.Message;
import session.Session;

/**
 * Слушает сообщения
 */
public interface MessageListener {

    void onMessage(Session session, Message message);
}
