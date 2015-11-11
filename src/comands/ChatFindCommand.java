package comands;

import message.*;
import net.SessionManager;
import session.Session;
import thread.AuthorizationService;

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

            return;
        } else {
            ArrayList<String> messagesByChat;
            ArrayList<String> findMessage = new ArrayList<>();
            ChatFindMessage chatFindMessage = (ChatFindMessage) message;
            if(messageLocalStore.getChatById(chatFindMessage.getIdChat())!=null) {
                messagesByChat = messageLocalStore.getChatById(chatFindMessage.getIdChat()).getMessageByChat();
                for (String str : messagesByChat) {
                    String[] tokens = str.split(" ");
                    for (String str1 : tokens) {
                        if (str1 != null && str1.equals(chatFindMessage.getStringFind())) {
                            findMessage.add(str);
                        }
                    }
                }
                ChatFindMessage chatFindMessage1 = new ChatFindMessage();
                chatFindMessage1.setType(CommandType.CHAT_FIND_OUT);
                chatFindMessage1.findingStringList = findMessage;

                try {
                    session.getConnectionHandler().send(chatFindMessage1);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    InfoMessage infoMessage = new InfoMessage();
                    infoMessage.setStringInfo("This chatId didn't find");
                    session.getConnectionHandler().send(infoMessage);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
