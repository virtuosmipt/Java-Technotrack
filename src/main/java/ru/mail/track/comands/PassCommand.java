package ru.mail.track.comands;

import ru.mail.track.message.*;
import ru.mail.track.net.SessionManager;
import ru.mail.track.session.Session;
import ru.mail.track.AuthorizationService;

import java.io.IOException;

/**
 * Created by a.borodin on 10.11.2015.
 */
public class PassCommand implements Command {

    private UserStore userLocalStore;
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

            Message infoMessage= new Message();
            infoMessage.setType(CommandType.USER_INFO);
            infoMessage.setInfoString("Please Login!!!");
            try {
                session.getConnectionHandler().send(infoMessage);

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            Message passMessage = (Message) message;
            if(passMessage.getArgs().length!=3 ){
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
                String oldPass = passMessage.getArgs()[1];
                String newPass = passMessage.getArgs()[2];
                if (oldPass != null &&oldPass.equals(session.getSessionUser().getPass())) {

                    session.getSessionUser().setPass(newPass);
                    userLocalStore.update(session.getSessionUser());
                    Message infoMessage = new Message();
                    infoMessage.setType(CommandType.USER_INFO);
                    infoMessage.setInfoString("You pass is changed");
                    try {
                        session.getConnectionHandler().send(infoMessage);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Message infoMessage = new Message();
                    infoMessage.setType(CommandType.USER_INFO);
                    infoMessage.setInfoString("Old Pass is not correct!");
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
