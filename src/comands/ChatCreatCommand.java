package comands;

import message.*;
import net.SessionManager;
import session.Session;
import thread.AuthorizationService;

import java.io.IOException;

/**
 * Created by a.borodin on 10.11.2015.
 */
public class ChatCreatCommand implements Command {
    private UserStore userLocalStore = new UserLocalStore();
    private SessionManager sessionManager;
    private AuthorizationService authorizationService ;
    private MessageLocalStore messageLocalStore;

    public ChatCreatCommand(UserLocalStore userLocalStore, SessionManager sessionManager, MessageLocalStore messageLocalStore) {
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
            boolean myBool=false;
            ChatCreatMessage chatCreatMessage = (ChatCreatMessage) message;
            chatCreatMessage.userIdList.add(session.getSessionUser().getId());
            for (Long userId : chatCreatMessage.userIdList) {
                Session userSession = sessionManager.getSessionByUser(userId);
                if (userSession != null&& (userSession.getSessionUser().getId()!=session.getSessionUser().getId())) {

                   myBool=true;
                }
            }
            if(myBool) {
                Chat chat = messageLocalStore.chatCreat(chatCreatMessage.userIdList);


                // Chat chat = messageStore.getChatById(sendMessage.getChatId());
                //  List<Long> parts = chat.getParticipantIds();
                InfoMessage infoMessage = new InfoMessage();
                infoMessage.setStringInfo("You Start chat with Users");
                try {
                    for (Long userId : chatCreatMessage.userIdList) {
                        Session userSession = sessionManager.getSessionByUser(userId);
                        if (userSession != null) {

                            userSession.getConnectionHandler().send(infoMessage);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    InfoMessage infoMessage= new InfoMessage();
                    infoMessage.setStringInfo("This Id Users is not Exist!");
                    session.getConnectionHandler().send(infoMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
