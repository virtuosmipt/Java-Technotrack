package ru.mail.track.net;

import ru.mail.track.comands.CommandType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.message.*;
import ru.mail.track.session.Session;

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


    private Protocol protocol;

    public ThreadedClient() {
        init();
    }

    @PostConstruct
    public void init() {
        try {
            protocol = new JsonProtocol();
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

                Message loginMessage = new Message(tokens);
                loginMessage.setType(CommandType.USER_LOGIN);
                handler.send(loginMessage);

                break;
            case "\\chat_history":
                Message chatHistoryMessage = new Message(tokens);
                chatHistoryMessage.setType(CommandType.CHAT_HISTORY);
                handler.send(chatHistoryMessage);
                break;
            case "\\help":
                HelpMessage helpMessage = new HelpMessage();
                for (String str : helpMessage.listCommand) {
                    System.out.println(str);
                }
                break;
            case "\\register":
                Message message = new Message(tokens);
                message.setType(CommandType.USER_REGISTER);
                //registerMessage.setLogin(tokens[1]);
                // registerMessage.setPass(tokens[2]);
                handler.send(message);
                break;
            case "\\user":
                Message userMessage = new Message(tokens);
                userMessage.setType(CommandType.USER_NAME);
                handler.send(userMessage);
                break;
            case "\\user_info":
                Message userInfoMessage = new Message(tokens);
                userInfoMessage.setType(CommandType.USER_INFORMATION);
                handler.send(userInfoMessage);
                break;
            case "\\user_pass":
                Message passMessage = new Message(tokens);
                passMessage.setType(CommandType.USER_PASS);
                handler.send(passMessage);
                break;
            case "\\chat_create":
                Message chatCreateMessage = new Message(tokens);
                chatCreateMessage.setType(CommandType.CHAT_CREAT);
                handler.send(chatCreateMessage);
                break;
            case "\\chat_list":
                Message chatListMessage = new Message(tokens);
                chatListMessage.setType(CommandType.CHAT_LIST);
                handler.send(chatListMessage);
                break;
            case "\\clear":
                System.out.println("\u001b[2J");
                System.out.println("\u001b[32m Welcome to Chat:)");
                break;

            case "\\chat_send":
                Message chatSendMessage = new Message(tokens);
                chatSendMessage.setType(CommandType.CHAT_SEND);
                handler.send(chatSendMessage);
                break;
            case "\\chat_find":
                Message chatFindMessage = new Message(tokens);
                chatFindMessage.setType(CommandType.CHAT_FIND);
                handler.send(chatFindMessage);
                break;

            default:
                System.out.println("Invalid input: " + line);

        }


    }

    /**
     * Получено сообщение из handler, как обрабатывать
     */
    @Override
    public void onMessage(Session session, Message msg) {
        Message message = (Message) msg;
        System.out.println(message.getType());
        if (message.getType() != null) {
            switch (message.getType().toString()) {

                case "USER_INFO":
                    System.out.println(message.getInfoString());
                    break;
                case "USER_LOGIN":
                    System.out.println(message.getInfoString());
                    break;
                case "CHAT_LIST":
                    System.out.println("Your ChatId:");
                    for(Long lg:message.getInfoLong()){
                        System.out.println(lg);
                    }
                    break;
                case "CHAT_HISTORY":
                    for(String str:message.getStringArrayList()){
                        System.out.println(str);
                    }
                    break;
                case "CHAT_FIND":
                    if(message.getStringArrayList().isEmpty()){
                        System.out.println("This word didn't findt:(");
                    }
                    else {
                        for (String str : message.getStringArrayList()) {
                            System.out.println(str);
                        }
                    }
                    break;
                default:

            }
        }
        else {
            System.out.println("Null^(");
        }
    }


}
