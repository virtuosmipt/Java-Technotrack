package ru.mail.track.comands;

import ru.mail.track.message.*;
import ru.mail.track.net.SessionManager;
import ru.mail.track.session.Session;
import ru.mail.track.thread.AuthorizationService;

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

       // if (session.getSessionUser() != null) {

        //    return;
       // } else {

            Message registerMessage= (Message) message;
           // System.out.println(registerMessage.getArgs().length);
            if(registerMessage.getArgs().length>3 || registerMessage.getArgs().length<=2){
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


                    String login = registerMessage.getArgs()[1];
                    String password = registerMessage.getArgs()[2];
                    //System.out.println(registerMessage.getLogin()+ ": "+registerMessage.getPass());
                    boolean isExist=false;
                    isExist=userLocalStore.isUserExist(login);
                    if (isExist==false){
                        User user= authorizationService.creatUser(login,password);
                        //System.out.println("UserId:"+user.getId());
                        Message infoMessage= new Message();
                        infoMessage.setType(CommandType.USER_INFO);
                        infoMessage.setInfoString("Successful!!! Please login, use comand \\login <Username> <Password>");
                        try {
                            session.getConnectionHandler().send(infoMessage);

                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        Message infoMessage= new Message();
                        infoMessage.setType(CommandType.USER_INFO);
                        infoMessage.setInfoString("Name is busy, enter another name!");
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
