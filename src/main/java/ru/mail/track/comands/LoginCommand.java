package ru.mail.track.comands;



import ru.mail.track.message.*;
import ru.mail.track.net.SessionManager;
import ru.mail.track.session.Session;
import ru.mail.track.thread.AuthorizationService;

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

            Message infoMessage= new Message();
            infoMessage.setType(CommandType.USER_INFO);
            infoMessage.setInfoString("You are already Login!!!");
            try {
                session.getConnectionHandler().send(infoMessage);

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Message loginMsg = (Message) msg;
            //System.out.println(loginMsg.getArgs().length);
            if(loginMsg.getArgs().length>3 || loginMsg.getArgs().length<=2){
                Message infoMessage= new Message();
                infoMessage.setType(CommandType.USER_INFO);
                infoMessage.setInfoString("ERROR, Invalid input Comand!!!");
                try {
                    session.getConnectionHandler().send(infoMessage);

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {


                    String login = loginMsg.getArgs()[1];
                    String password = loginMsg.getArgs()[2];
                    //authorizationService.startAuthorization();
                    isExist = userLocalStore.isUserExist(login, password);
                    if (isExist) {
                       // System.out.println("sush");
                        User user = userLocalStore.getUser(login, password);
                        session.setSessionUser(user);
                        //user.setId(1L);
                        // System.out.println(user.getName());
                        sessionManager.registerUser(user.getId(), session.getId());
                        Message sendMessage = new Message();
                        sendMessage.setType(CommandType.USER_LOGIN);
                        sendMessage.setInfoString("You are  login");
                        //sendMessage.setChatId(user.getId());
                        try {
                            session.getConnectionHandler().send(sendMessage);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //System.out.println(loginMsg.getLogin());
                        // authorizationService.creatUser(loginMsg.getLogin(), loginMsg.getPass());
                        //  User user = userLocalStore.getUser(loginMsg.getLogin(), loginMsg.getPass());
                        //session.setSessionUser(user);
                        //user.setId(1L);
                        // System.out.println(user.getName());
                        // sessionManager.registerUser(user.getId(), session.getId());
                        //  System.out.println(session.getSessionUser().getId());
                        //InfoMessage infoMessage = new InfoMessage(session);
                        Message infoMessage = new Message();
                        infoMessage.setType(CommandType.USER_INFO);
                        infoMessage.setInfoString("You are didn't find in our base, please enter \\login");
                        //sendMessage.setChatId(user.getId());
                        try {
                            session.getConnectionHandler().send(infoMessage);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
