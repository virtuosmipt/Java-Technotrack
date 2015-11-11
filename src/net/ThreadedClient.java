package net;

import comands.CommandType;
import message.*;

import session.Session;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


/**
 * Клиентская часть
 */
public class ThreadedClient implements MessageListener {

    public static final int PORT = 19000;
    public static final String HOST = "localhost";

    ConnectionHandler handler;


    private Protocol protocol = new StringProtocol();

    public ThreadedClient() {
        init();
    }

    @PostConstruct
    public void init() {
        try {
            Socket socket = new Socket(HOST, PORT);
            Session session = new Session();
            handler = new SocketConnectionHandler(protocol, session, socket);

            // Этот класс будет получать уведомления от socket handler
            handler.addListener(this);

            Thread socketHandler = new Thread(handler);
            socketHandler.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
            // exit, failed to open socket
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("\u001b[32m Welcome to Chat:)");
        Protocol protocol = new StringProtocol();
        ThreadedClient client = new ThreadedClient();

        Scanner scanner = new Scanner(System.in);
        System.out.println("$");
        while (true) {
           // System.out.println("\u001b[2J");
            //System.out.println("\u001b[32m Welcome to Chat:)");
            String input = scanner.nextLine();
            if ("q".equals(input)) {
                return;
            }

            client.processInput(input);
        }
    }

    public void processInput(String line) throws IOException {
        String[] tokens = line.split(" ");

        String cmdType = tokens[0];
        switch (cmdType) {
            case "\\login":

                    LoginMessage loginMessage = new LoginMessage();
                    loginMessage.setType(CommandType.USER_LOGIN);
                    loginMessage.setLogin(tokens[1]);
                    loginMessage.setPass(tokens[2]);
                    handler.send(loginMessage);

                break;
            case "send":
                SendMessage sendMessage = new SendMessage();
                sendMessage.setType(CommandType.MSG_SEND);
                sendMessage.setChatId(Long.parseLong(tokens[1]));
                sendMessage.setMessage(tokens[2]);
                handler.send(sendMessage);
                break;
            case "\\help":
                HelpMessage helpMessage = new HelpMessage();
                for(String str: helpMessage.listCommand){
                    System.out.println(str);
                }
                break;
            case "\\" +
                    "" +
                    "register":
                RegisterMessage registerMessage = new RegisterMessage();
                registerMessage.setType(CommandType.USER_REGISTER);
                registerMessage.setLogin(tokens[1]);
                registerMessage.setPass(tokens[2]);
                handler.send(registerMessage);
                break;
            case "\\user":
                UserMessage userMessage = new UserMessage();
                userMessage.setType(CommandType.USER_NAME);
                userMessage.setLogin(tokens[1]);
                handler.send(userMessage);
                break;
            case "\\user_info":
                UserInfoMessage userInfoMessage = new UserInfoMessage();
                userInfoMessage.setType(CommandType.USER_INFORMATION);
                if(tokens.length>1){


                    try {

                        Long id = Long.valueOf(tokens[1]);
                        userInfoMessage.setId(id);
                        handler.send(userInfoMessage);

                    } catch (NumberFormatException e) {
                        System.err.println("Неверный формат строки!");
                    }


                }
                else{
                    userInfoMessage.setId(null);
                    handler.send(userInfoMessage);
                }
                break;
            case "\\user_pass":
                PassMessage passMessage = new PassMessage();
                passMessage.setType(CommandType.USER_PASS);
                passMessage.setOldPass(tokens[1]);
                passMessage.setNewPass(tokens[2]);
                handler.send(passMessage);
                break;
            case "\\chat_creat":
                ChatCreatMessage chatCreatMessage = new ChatCreatMessage();
                chatCreatMessage.setType(CommandType.CHAT_CREAT);
                if(tokens.length>1){
                    for(int i=1;i<tokens.length;i++){
                        chatCreatMessage.userIdList.add(Long.valueOf(tokens[i]));
                    }
                    handler.send(chatCreatMessage);
                }
                break;
            case "\\chat_list":
                ChatListMessage chatListMessage = new ChatListMessage();
                chatListMessage.setType(CommandType.CHAT_LIST);
                handler.send(chatListMessage);
                break;
            case "\\clear":
                System.out.println("\u001b[2J");
                System.out.println("\u001b[32m Welcome to Chat:)");
                break;
            case "\\chat_find":
                ChatFindMessage chatFindMessage = new ChatFindMessage();
                chatFindMessage.setType(CommandType.CHAT_FIND);
                chatFindMessage.setStringFind(tokens[2]);
                chatFindMessage.setIdChat(Long.valueOf(tokens[1]));
                handler.send(chatFindMessage);
                break;

            case "\\chat_send":
                ChatSendMessage chatSendMessage = new ChatSendMessage();
                StringBuilder builder = new StringBuilder();
                chatSendMessage.setType(CommandType.CHAT_SEND);
                if(tokens.length>2){
                    for(int i=1;i<tokens.length;i++){
                        try {
                            chatSendMessage.chatsId.add(Long.valueOf(tokens[i]));
                        }
                        catch (NumberFormatException e){
                            for(int k=i;k<tokens.length;k++){

                                builder.append(tokens[k]).append(" ");

                            }
                            chatSendMessage.setMessages(builder.toString());
                            System.out.println(chatSendMessage.getMessages());
                            break;
                        }
                    }

                    handler.send(chatSendMessage);
                }
                else{
                    System.out.println("Invalid input!");
                }
                break;

            default:
                System.out.println("Invalid input: " + line);

        }



    }

    /**
     * Получено сообщение из handler, как обрабатывать
     *
     */
    @Override
    public void onMessage(Session session, Message msg) {
        if(msg.getType().toString().equals("CHAT_LIST")){
            ChatListMessage chatListMessage = (ChatListMessage) msg;
            System.out.println("You have this chatId");
            if(chatListMessage.chatsId.isEmpty()){
                System.out.println("you don't have chats");
            }
            else {
                for (Long lg : chatListMessage.chatsId) {
                    System.out.println(lg + ";");
                }
            }
        }
        /*if(msg.getType().toString().equals("CHAT_FIND_OUT")){
            ChatFindMessage chatFindMessage = (ChatFindMessage) msg;
            for(String str: chatFindMessage.findingStringList){
                System.out.println(str);
            }
        }*/
        else {
            System.out.println(msg.getType());
            System.out.printf("%s", ((SendMessage) msg).getMessage());
            System.out.println();
        }
    }

}
