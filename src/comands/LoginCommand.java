package comands;


import message.*;
import net.SessionManager;
import session.Session;
import thread.AuthorizationService;

/**
 * Выполняем авторизацию по этой команде
 */
public class LoginCommand implements Command {



    private UserStore userLocalStore;
    private SessionManager sessionManager;
    private AuthorizationService authorizationService;

    public LoginCommand(UserLocalStore userLocalStore, SessionManager sessionManager) {
        this.userLocalStore = userLocalStore;
        this.sessionManager = sessionManager;
    }


    @Override
    public void execute(Session session, Message msg) {
    boolean isExist=false;
        if (session.getSessionUser() != null) {

            return;
        } else {
            LoginMessage loginMsg = (LoginMessage) msg;
            //authorizationService.startAuthorization();
            isExist=userLocalStore.isUserExist(loginMsg.getLogin());
            if(isExist){
                User user = userLocalStore.getUser(loginMsg.getLogin(), loginMsg.getPass());
                session.setSessionUser(user);
                // System.out.println(user.getName());
                sessionManager.registerUser(user.getId(), session.getId());
            }
            else{
                System.out.println(loginMsg.getLogin());
                authorizationService.creatUser(loginMsg.getLogin(), loginMsg.getPass());
                User user = userLocalStore.getUser(loginMsg.getLogin(), loginMsg.getPass());
                session.setSessionUser(user);
                // System.out.println(user.getName());
                sessionManager.registerUser(user.getId(), session.getId());
            }


        }
        /*
        А эта часть у нас уже реализована
        1 проверим, есть ли у нас уже юзер сессии
        2 посмотрим на аргументы команды
        3 пойдем в authorizationService и попробуем получить юзера
         */
    }
}
