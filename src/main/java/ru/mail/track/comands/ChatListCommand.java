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
public class ChatListCommand implements Command {


    private UserStore userLocalStore ;
    private SessionManager sessionManager;
    private AuthorizationService authorizationService ;
    private MessageLocalStore messageLocalStore;

    public ChatListCommand(UserLocalStore userLocalStore, SessionManager sessionManager,MessageLocalStore messageLocalStore) {
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
            Message chatListMessage = (Message) message;
            if(chatListMessage.getArgs().length>1){
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
                ArrayList<Long> chats;
                chats = messageLocalStore.getChatsByUserId(session.getSessionUser().getId());
                Message infoMessage= new Message();
                infoMessage.setInfoLong(chats);
                infoMessage.setType(CommandType.CHAT_LIST);



                try {
                    session.getConnectionHandler().send(infoMessage);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
