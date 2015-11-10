package comands;


import message.*;
import net.SessionManager;
import session.Session;
import thread.AuthorizationService;

import java.io.IOException;

/**
 * Выполняем авторизацию по этой команде
 */
public class LoginCommand implements Command {



    private UserStore userLocalStore = new UserLocalStore();
    private SessionManager sessionManager;
    private AuthorizationService authorizationService ;

    public LoginCommand(UserLocalStore userLocalStore, SessionManager sessionManager) {
        this.userLocalStore = userLocalStore;
        this.sessionManager = sessionManager;
        authorizationService = new AuthorizationService(userLocalStore);
    }


    @Override
    public void execute(Session session, Message msg) {
        boolean isExist=false;
        if (session.getSessionUser() != null) {

            return;
        } else {
            LoginMessage loginMsg = (LoginMessage) msg;
            //authorizationService.startAuthorization();
            isExist=userLocalStore.isUserExist(loginMsg.getLogin(),loginMsg.getPass());
            if(isExist){
                System.out.println("sush");
                User user = userLocalStore.getUser(loginMsg.getLogin(), loginMsg.getPass());
                session.setSessionUser(user);
                //user.setId(1L);
                // System.out.println(user.getName());
                sessionManager.registerUser(user.getId(), session.getId());
                SendMessage sendMessage = new SendMessage();
                sendMessage.setMessage("You are  login");
                sendMessage.setChatId(user.getId());
                try {
                    session.getConnectionHandler().send(sendMessage);

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                //System.out.println(loginMsg.getLogin());
               // authorizationService.creatUser(loginMsg.getLogin(), loginMsg.getPass());
              //  User user = userLocalStore.getUser(loginMsg.getLogin(), loginMsg.getPass());
                //session.setSessionUser(user);
                //user.setId(1L);
                // System.out.println(user.getName());
               // sessionManager.registerUser(user.getId(), session.getId());
              //  System.out.println(session.getSessionUser().getId());
                //InfoMessage infoMessage = new InfoMessage(session);
               InfoMessage infoMessage= new InfoMessage();
                infoMessage.setType(CommandType.USER_INFO);
                infoMessage.setStringInfo("You are didn't find in our base, please enter \\login");
                //sendMessage.setChatId(user.getId());
                try {
                    session.getConnectionHandler().send(infoMessage);

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
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
