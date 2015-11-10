package comands;

import message.*;
import net.SessionManager;
import session.Session;
import thread.AuthorizationService;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by a.borodin on 10.11.2015.
 */
public class UserInfoCommand implements Command {

    private UserStore userLocalStore = new UserLocalStore();
    private SessionManager sessionManager;
    private AuthorizationService authorizationService ;

    public UserInfoCommand(UserLocalStore userLocalStore, SessionManager sessionManager) {
        this.userLocalStore = userLocalStore;
        this.sessionManager = sessionManager;
        authorizationService = new AuthorizationService(userLocalStore);
    }

    @Override
    public void execute(Session session, Message message) {
        if (session.getSessionUser() == null) {

            return;
        } else {
            System.out.println("Info starting...");
            UserInfoMessage userInfoMessage = (UserInfoMessage) message;
            if(userInfoMessage.getId()==null){
                InfoMessage infoMessage= new InfoMessage();
                infoMessage.setStringInfo("You Username:" + session.getSessionUser().getName()
                        + "\n" + "You Password: " + session.getSessionUser().getPass());
                try {
                    session.getConnectionHandler().send(infoMessage);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            else{
                InfoMessage infoMessage= new InfoMessage();
                userLocalStore.getUserById(new AtomicLong(message.getId()));
                infoMessage.setStringInfo("You Username:" + userLocalStore.getUserById(new AtomicLong(message.getId())).getName()
                        + "\n" + "You Password: " + userLocalStore.getUserById(new AtomicLong(message.getId())).getPass());
                try {
                    session.getConnectionHandler().send(infoMessage);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }

        }
    }
}
