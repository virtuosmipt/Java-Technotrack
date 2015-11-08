package comands;

import message.Chat;
import message.Message;
import message.MessageStore;
import message.SendMessage;
import net.SessionManager;
import session.Session;

import java.io.IOException;
import java.util.List;

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

        SendMessage sendMessage = (SendMessage) message;
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
        }
    }
}
