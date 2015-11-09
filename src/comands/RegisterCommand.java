package comands;

import message.*;
import net.SessionManager;
import session.Session;
import thread.AuthorizationService;

import java.io.IOException;

/**
 * Created by a.borodin on 09.11.2015.
 */
public class RegisterCommand implements Command {
    private UserStore userLocalStore = new UserLocalStore();
    private SessionManager sessionManager;
    private AuthorizationService authorizationService ;

    public  RegisterCommand(UserLocalStore userLocalStore, SessionManager sessionManager){
        this.userLocalStore = userLocalStore;
        this.sessionManager = sessionManager;
        authorizationService = new AuthorizationService(userLocalStore);
    }
    @Override
    public void execute(Session session, Message message) {

        if (session.getSessionUser() != null) {

            return;
        } else {
            System.out.println("registration start...");
            RegisterMessage registerMessage= (RegisterMessage) message;
            System.out.println(registerMessage.getLogin()+ ": "+registerMessage.getPass());
            boolean isExist=false;
            isExist=userLocalStore.isUserExist(registerMessage.getLogin());
            if (isExist==false){
                authorizationService.creatUser(registerMessage.getLogin(),registerMessage.getLogin());
                InfoMessage infoMessage= new InfoMessage();
                infoMessage.setStringInfo("Successful! Please login, use comand \\login <Username> <Password>");
                try {
                    session.getConnectionHandler().send(infoMessage);

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                InfoMessage infoMessage= new InfoMessage();
                infoMessage.setType(CommandType.USER_INFO);
                infoMessage.setStringInfo("Name is busy, enter another name!");
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
