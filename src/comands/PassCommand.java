package comands;

import message.*;
import net.SessionManager;
import session.Session;
import thread.AuthorizationService;

import java.io.IOException;

/**
 * Created by a.borodin on 10.11.2015.
 */
public class PassCommand implements Command {

    private UserStore userLocalStore = new UserLocalStore();
    private SessionManager sessionManager;
    private AuthorizationService authorizationService ;

    public PassCommand(UserLocalStore userLocalStore, SessionManager sessionManager) {
        this.userLocalStore = userLocalStore;
        this.sessionManager = sessionManager;
        authorizationService = new AuthorizationService(userLocalStore);
    }
    @Override
    public void execute(Session session, Message message) {
        if (session.getSessionUser() == null) {

            return;
        } else {

            PassMessage passMessage = (PassMessage) message;
            if(passMessage.getOldPass()!=null && passMessage.getOldPass().equals(session.getSessionUser().getPass())){

                session.getSessionUser().setPass(passMessage.getNewPass());
                InfoMessage infoMessage = new InfoMessage();
                infoMessage.setStringInfo("You pass is changed");
                try {
                    session.getConnectionHandler().send(infoMessage);

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                InfoMessage infoMessage = new InfoMessage();
                infoMessage.setStringInfo("Old Pass is not correct!");
                try {
                    session.getConnectionHandler().send(infoMessage);

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
