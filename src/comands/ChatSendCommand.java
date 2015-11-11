package comands;

import message.*;
import net.SessionManager;
import session.Session;
import thread.AuthorizationService;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by a.borodin on 11.11.2015.
 */
public class ChatSendCommand implements Command {
    private UserStore userLocalStore = new UserLocalStore();
    private SessionManager sessionManager;
    private AuthorizationService authorizationService ;
    private MessageLocalStore messageLocalStore;

    public ChatSendCommand(UserLocalStore userLocalStore, SessionManager sessionManager,MessageLocalStore messageLocalStore) {
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
            ChatSendMessage chatSendMessage = (ChatSendMessage) message;
            ArrayList<Long> myChats;
            ArrayList<Long> usersByChat;
            myChats = messageLocalStore.getChatsByUserId(session.getSessionUser().getId());
            for (Long lg: myChats){
                for (Long lg1:chatSendMessage.chatsId){
                    if(lg1==lg){

                        messageLocalStore.getChatById(lg1).getMessageByChat().add(chatSendMessage.getMessages());
                        usersByChat= messageLocalStore.getUsersByChat(lg1);
                        InfoMessage infoMessage = new InfoMessage();
                        infoMessage.setStringInfo(chatSendMessage.getMessages());
                        try {
                            for (Long userId : usersByChat) {
                                Session userSession = sessionManager.getSessionByUser(userId);
                                if (userSession != null) {
                                    userSession.getConnectionHandler().send(infoMessage);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
