package ru.mail.track.comands;

import ru.mail.track.message.Message;
import ru.mail.track.message.MessageStore;
import ru.mail.track.net.SessionManager;
import ru.mail.track.session.Session;

/**
 *
 */
public class SendCommand implements Command {

    private MessageStore messageStore;
    private SessionManager sessionManager;

    public SendCommand(SessionManager sessionManager, MessageStore messageStore) {
        this.sessionManager = sessionManager;
        this.messageStore = messageStore;
    }

    @Override
    public void execute(Session session, Message message) {

       /* SendMessage sendMessage = (SendMessage) message;
        Chat chat = messageStore.getChatById(sendMessage.getChatId());
        List<Long> parts = chat.getParticipantIds();
        try {
            for (Long userId : parts) {
                Session userSession = sessionManager.getSessionByUser(userId);
                if (userSession != null) {
                    userSession.getConnectionHandler().send(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
