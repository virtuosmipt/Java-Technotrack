package comands;

import message.*;
import net.SessionManager;
import session.Session;
import thread.AuthorizationService;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by a.borodin on 10.11.2015.
 */
public class ChatListCommand implements Command {


    private UserStore userLocalStore ;
    private SessionManager sessionManager;
    private AuthorizationService authorizationService ;
    private MessageLocalStore messageLocalStore;

    public ChatListCommand(UserLocalStore userLocalStore, SessionManager sessionManager,MessageLocalStore messageLocalStore) {
        this.userLocalStore = userLocalStore;
        this.sessionManager = sessionManager;
        this.messageLocalStore = messageLocalStore;
        authorizationService = new AuthorizationService(userLocalStore);
    }
    @Override
    public void execute(Session session, Message message) {
        if (session.getSessionUser() == null) {

            return;
        } else {
            ArrayList<Long> chats;
            chats = messageLocalStore.getChatsByUserId(session.getSessionUser().getId());
            ChatListMessage chatListMessage = (ChatListMessage) message;
            chatListMessage.setType(CommandType.CHAT_LIST);
            chatListMessage.chatsId = chats;
            try {
                session.getConnectionHandler().send(chatListMessage);

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
