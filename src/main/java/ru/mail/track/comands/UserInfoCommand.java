package ru.mail.track.comands;

import ru.mail.track.message.*;
import ru.mail.track.net.SessionManager;
import ru.mail.track.session.Session;
import ru.mail.track.AuthorizationService;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by a.borodin on 10.11.2015.
 */
public class UserInfoCommand implements Command {

    private UserStore userLocalStore ;
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
            System.out.println("null user");
            Message infoMessage= new Message();
            infoMessage.setType(CommandType.USER_INFO);
            infoMessage.setInfoString("Please  Login!!!");
            try {
                session.getConnectionHandler().send(infoMessage);

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //System.out.println("Info starting...");
            Message userInfoMessage = (Message) message;
            if(userInfoMessage.getArgs().length>2 ){
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
                if (userInfoMessage.getArgs().length==1) {
                    Message infoMessage = new Message();
                    infoMessage.setType(CommandType.USER_INFO);
                    infoMessage.setInfoString("You Username:" + session.getSessionUser().getName()
                            + "\n" + "You Password: " + session.getSessionUser().getPass()
                            + "\n" + "UserId:" + session.getSessionUser().getId());
                    try {
                        session.getConnectionHandler().send(infoMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Message infoMessage = new Message();
                    infoMessage.setType(CommandType.USER_INFO);
                    try {
                        Long id = Long.valueOf(userInfoMessage.getArgs()[1]);
                        User user = userLocalStore.getUser(id);
                        //System.out.println(user.getName());
                        if(user!=null) {
                            infoMessage.setInfoString("\n" + "You Username:" + user.getName()
                                    + "\n" + "You Password: " + user.getPass()
                                    + "\n" + "you ID: " + user.getId());
                            try {
                                session.getConnectionHandler().send(infoMessage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else {Message newInfoMessage = new Message();
                            newInfoMessage.setType(CommandType.USER_INFO);
                            newInfoMessage.setInfoString("This Id is not exist!");

                            try {
                                session.getConnectionHandler().send(newInfoMessage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }catch (NumberFormatException e) {
                        Message newInfoMessage = new Message();
                        newInfoMessage.setType(CommandType.USER_INFO);
                        newInfoMessage.setInfoString("Invalid input Command!");
                        try {
                            session.getConnectionHandler().send(newInfoMessage);

                        } catch (IOException ee) {
                            ee.printStackTrace();
                        }

                    }


                }
            }

        }
    }
}
