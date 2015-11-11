package comands;

import message.*;
import net.SessionManager;
import session.Session;
import thread.AuthorizationService;

import java.io.IOException;

/**
 * Created by a.borodin on 10.11.2015.
 */
public class UserCommand implements Command {

    private UserStore userLocalStore = new UserLocalStore();
    private SessionManager sessionManager;
    private AuthorizationService authorizationService ;

    public UserCommand(UserLocalStore userLocalStore, SessionManager sessionManager) {
        this.userLocalStore = userLocalStore;
        this.sessionManager = sessionManager;
        authorizationService = new AuthorizationService(userLocalStore);
    }
    @Override
    public void execute(Session session, Message message) {
        if (session.getSessionUser() == null) {

            return;
        }
        else{
            UserMessage userMessage= (UserMessage) message;
            session.getSessionUser().setName(((UserMessage) message).getLogin());
            InfoMessage infoMessage = new InfoMessage();
            infoMessage.setStringInfo("You new login: "+ userMessage.getLogin());
            try{
                session.getConnectionHandler().send(infoMessage);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
