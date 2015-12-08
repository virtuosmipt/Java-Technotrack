package ru.mail.track.comands;

import ru.mail.track.message.*;
import ru.mail.track.net.SessionManager;
import ru.mail.track.session.Session;
import ru.mail.track.AuthorizationService;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by a.borodin on 11.11.2015.
 */
public class ChatFindCommand implements Command {
    private UserStore userLocalStore ;
    private SessionManager sessionManager;
    private AuthorizationService authorizationService ;
    private MessageLocalStore messageLocalStore;

    public ChatFindCommand(UserLocalStore userLocalStore, SessionManager sessionManager,MessageLocalStore messageLocalStore) {
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
        } else {Message chatFindMessage = (Message) message;
            if (chatFindMessage.getArgs().length !=3) {
                Message infoMessage = new Message();
                infoMessage.setType(CommandType.USER_INFO);
                infoMessage.setInfoString("ERROR, Invalid input Comand!!!");
                try {
                    session.getConnectionHandler().send(infoMessage);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    ArrayList<String> wordList = new ArrayList<>();
                    Message infoMessage = new Message();
                    infoMessage.setType(CommandType.CHAT_FIND);
                    String word = chatFindMessage.getArgs()[2];
                    Long idChat = Long.valueOf(chatFindMessage.getArgs()[1]);
                    if(messageLocalStore.getChatById(idChat)==null){
                        Message infonewMessage = new Message();
                        infonewMessage.setType(CommandType.USER_INFO);
                        infonewMessage.setInfoString("This chat is not exist!!!");
                        try {
                            session.getConnectionHandler().send(infonewMessage);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        for (String str : messageLocalStore.getChatById(idChat).getMessageByChat()) {
                            String[] tokens = str.split(" ");
                            for (String str1 : tokens) {
                                if (str1 != null && str1.equals(word)) {
                                    wordList.add(str);
                                }
                            }
                        }
                        infoMessage.setStringArrayList(wordList);

                        try {
                            session.getConnectionHandler().send(infoMessage);
                        } catch (IOException ee) {
                            ee.printStackTrace();
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
