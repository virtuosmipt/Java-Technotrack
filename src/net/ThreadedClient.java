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
        Protocol protocol = new StringProtocol();
        ThreadedClient client = new ThreadedClient();

        Scanner scanner = new Scanner(System.in);
        System.out.println("$");
        while (true) {
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
                helpMessage.setType(CommandType.USER_HELP);
                handler.send(helpMessage);
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
        System.out.printf("%s", ((SendMessage) msg).getMessage());
        System.out.println();
    }

}
