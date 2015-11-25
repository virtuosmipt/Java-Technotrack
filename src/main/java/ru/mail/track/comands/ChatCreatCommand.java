package ru.mail.track.comands;

import ru.mail.track.net.SessionManager;
import ru.mail.track.message.*;
import ru.mail.track.session.Session;
import ru.mail.track.thread.AuthorizationService;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by a.borodin on 10.11.2015.
 */
public class ChatCreatCommand implements Command {
    private UserStore userLocalStore = new UserLocalStore();
    private SessionManager sessionManager;
    private AuthorizationService authorizationService ;
    private MessageLocalStore messageLocalStore;

    public ChatCreatCommand(UserLocalStore userLocalStore, SessionManager sessionManager, MessageLocalStore messageLocalStore) {
        this.userLocalStore = userLocalStore;
        this.sessionManager = sessionManager;
        this.messageLocalStore = messageLocalStore;
        authorizationService = new AuthorizationService(userLocalStore);
    }

    @Override
    public void execute(Session session, Message message) {
        if (session.getSessionUser() == null) {

            Message infoMessage= new Message();
            infoMessage.setType(CommandType.USER_INFO);
            infoMessage.setInfoString("You are not Login!!!");
            try {
                session.getConnectionHandler().send(infoMessage);

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            boolean myBool=false;
            Message chatCreatMessage = (Message) message;
            if(chatCreatMessage.getArgs().length<2){
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
                ArrayList<Long> userIdList = new ArrayList<>();
                boolean mybool=false;
                for(int i =1;i< chatCreatMessage.getArgs().length;i++){
                    try {
                        userIdList.add(Long.valueOf(chatCreatMessage.getArgs()[i]));
                    }catch (NumberFormatException e) {
                        Message newInfoMessage = new Message();
                        newInfoMessage.setType(CommandType.USER_INFO);
                        newInfoMessage.setInfoString("Invalid input Command!");
                        mybool=true;
                        try {System.out.println("chat creating...");
                            session.getConnectionHandler().send(newInfoMessage);

                        } catch (IOException ee) {
                            ee.printStackTrace();
                        }

                    }
                }

                if(mybool==false) {
                    userIdList.add(session.getSessionUser().getId());

                    for (Long userId : userIdList) {
                        Session userSession = sessionManager.getSessionByUser(userId);
                        if (userSession != null && (userSession.getSessionUser().getId() != session.getSessionUser().getId())) {

                            myBool = true;//есть ли хоть один онлайн
                        }
                    }
                    if (myBool) {

                       messageLocalStore.chatCreat(userIdList);


                        // Chat chat = messageStore.getChatById(sendMessage.getChatId());
                        //  List<Long> parts = chat.getParticipantIds();
                        Message infoMessage = new Message();
                        infoMessage.setType(CommandType.USER_INFO);
                        infoMessage.setInfoString("You Start chat with Users");
                        try {
                            for (Long userId : userIdList) {
                                System.out.println(userId);
                                Session userSession = sessionManager.getSessionByUser(userId);

                                if (userSession != null) {

                                    userSession.getConnectionHandler().send(infoMessage);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            Message infoMessage = new Message();
                            infoMessage.setType(CommandType.USER_INFO);
                            infoMessage.setInfoString("This Id Users is not Exist!");
                            session.getConnectionHandler().send(infoMessage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
