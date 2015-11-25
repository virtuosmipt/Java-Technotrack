package ru.mail.track.comands;

import ru.mail.track.message.Message;
import ru.mail.track.message.MessageLocalStore;
import ru.mail.track.message.UserLocalStore;
import ru.mail.track.message.UserStore;
import ru.mail.track.net.SessionManager;
import ru.mail.track.session.Session;
import ru.mail.track.thread.AuthorizationService;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by a.borodin on 23.11.2015.
 */
public class ChatHistoryCommand implements Command {
    private UserStore userLocalStore;
    private SessionManager sessionManager;
    private AuthorizationService authorizationService;
    private MessageLocalStore messageLocalStore;

    public ChatHistoryCommand(UserLocalStore userLocalStore, SessionManager sessionManager, MessageLocalStore messageLocalStore) {
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
            Message chatHistoryMessage = (Message) message;
            if (chatHistoryMessage.getArgs().length != 2) {
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
                    Long idChat = Long.valueOf(chatHistoryMessage.getArgs()[1]);
                    Message historyMessage = new Message();
                    historyMessage.setType(CommandType.CHAT_HISTORY);
                    if(messageLocalStore.getChatById(idChat)==null){
                        Message newInfoMessage = new Message();
                        newInfoMessage.setType(CommandType.USER_INFO);
                        newInfoMessage.setInfoString("This chat is not exist!");
                        try {
                            session.getConnectionHandler().send(newInfoMessage);

                        } catch (IOException ee) {
                            ee.printStackTrace();
                        }
                    }
                    else {
                        boolean isUserHasChat=false;
                        for(Long lg:messageLocalStore.getUsersByChat(idChat)){
                            if(session.getSessionUser().getId()==lg){
                                isUserHasChat=true;
                            }
                        }
                            if(isUserHasChat) {
                                historyMessage.setStringArrayList(messageLocalStore.getChatById(idChat).getMessageByChat());
                                try {
                                    session.getConnectionHandler().send(historyMessage);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        else {
                                Message newInfoMessage = new Message();
                                newInfoMessage.setType(CommandType.USER_INFO);
                                newInfoMessage.setInfoString("You don't have this Chat!");
                                try {
                                    session.getConnectionHandler().send(newInfoMessage);

                                } catch (IOException ee) {
                                    ee.printStackTrace();
                                }
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
