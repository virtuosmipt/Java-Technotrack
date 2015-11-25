package ru.mail.track.comands;


import ru.mail.track.net.SessionManager;
import ru.mail.track.message.*;
import ru.mail.track.session.Session;
import ru.mail.track.thread.AuthorizationService;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by a.borodin on 11.11.2015.
 */
public class ChatSendCommand implements Command {
    private UserStore userLocalStore = new UserLocalStore();
    private SessionManager sessionManager;
    private AuthorizationService authorizationService;
    private MessageLocalStore messageLocalStore;

    public ChatSendCommand(UserLocalStore userLocalStore, SessionManager sessionManager, MessageLocalStore messageLocalStore) {
        this.userLocalStore = userLocalStore;
        this.sessionManager = sessionManager;
        this.messageLocalStore = messageLocalStore;
        authorizationService = new AuthorizationService(userLocalStore);
    }

    @Override
    public void execute(Session session, Message message) {
        if (session.getSessionUser() == null) {

            Message infoMessage = new Message();
            infoMessage.setType(CommandType.USER_INFO);
            infoMessage.setInfoString("You are not Login!!!");
            try {
                session.getConnectionHandler().send(infoMessage);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            Message chatSendMessage = (Message) message;
            if (chatSendMessage.getArgs().length < 3) {
                Message infoMessage = new Message();
                infoMessage.setType(CommandType.USER_INFO);
                infoMessage.setInfoString("ERROR, Invalid input Comand!!!");
                try {
                    session.getConnectionHandler().send(infoMessage);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    boolean isChatExist = false;
                    ArrayList<Long> myChats;
                    ArrayList<Long> usersByChat;
                    String sendMessage = new String();
                    Long idChat = Long.valueOf(chatSendMessage.getArgs()[1]);
                    myChats = messageLocalStore.getChatsByUserId(session.getSessionUser().getId());
                    for (Long lg : myChats) {
                        if (idChat == lg) {
                            isChatExist = true;

                        }

                    }
                    for (int i = 2; i < chatSendMessage.getArgs().length; i++) {
                        sendMessage = sendMessage.concat(chatSendMessage.getArgs()[i]).concat(" ");
                    }
                    messageLocalStore.getChatById(idChat).getMessageByChat().add(sendMessage);
                    usersByChat = messageLocalStore.getUsersByChat(idChat);
                    Message infoMessage = new Message();
                    infoMessage.setType(CommandType.USER_INFO);
                    infoMessage.setInfoString(sendMessage);
                    try {
                        for (Long userId : usersByChat) {
                            Session userSession = sessionManager.getSessionByUser(userId);
                            if (userSession != null) {
                                userSession.getConnectionHandler().send(infoMessage);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (isChatExist == false) {
                        Message newInfoMessage = new Message();
                        newInfoMessage.setType(CommandType.USER_INFO);
                        newInfoMessage.setInfoString("Chat is not exist!!!");
                        try {
                            session.getConnectionHandler().send(newInfoMessage);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                } catch (NumberFormatException e) {
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


