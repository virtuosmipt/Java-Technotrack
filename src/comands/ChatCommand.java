package comands;

import message.*;
import net.SessionManager;
import session.Session;
import thread.AuthorizationService;

/**
 * Created by a.borodin on 10.11.2015.
 */
public class ChatCommand implements Command {
    private UserStore userLocalStore = new UserLocalStore();
    private SessionManager sessionManager;
    private AuthorizationService authorizationService ;
    private MessageLocalStore messageLocalStore;

    public ChatCommand(UserLocalStore userLocalStore, SessionManager sessionManager, MessageLocalStore messageLocalStore) {
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
            ChatMessage chatMessage = (ChatMessage) message;
            Chat chat = new Chat();
            for(Long id: chatMessage.userIdList){
                chat.addParticipant(id);

            }

        }
    }
}
