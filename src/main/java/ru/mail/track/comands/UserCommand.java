package ru.mail.track.comands;

import ru.mail.track.message.*;
import ru.mail.track.net.SessionManager;
import ru.mail.track.session.Session;
import ru.mail.track.thread.AuthorizationService;

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

            Message infoMessage= new Message();
            infoMessage.setType(CommandType.USER_INFO);
            infoMessage.setInfoString("You are not  Login!!!");
            try {
                session.getConnectionHandler().send(infoMessage);

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{

            Message userMessage= (Message) message;
            if(userMessage.getArgs().length!=2){
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
            else{
                String login = userMessage.getArgs()[1];
                if(userLocalStore.isUserExist(login)){
                    Message infoMessage= new Message();
                    infoMessage.setType(CommandType.USER_INFO);
                    infoMessage.setInfoString("This name is busy!!!");
                    try {
                        session.getConnectionHandler().send(infoMessage);

                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    session.getSessionUser().setName(login);
                    Message infoMessage = new Message();
                    infoMessage.setType(CommandType.USER_INFO);
                    infoMessage.setInfoString("You new login: " + login);
                    try {
                        session.getConnectionHandler().send(infoMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
