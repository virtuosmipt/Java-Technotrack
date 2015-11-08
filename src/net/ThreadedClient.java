package net;

import comands.CommandType;
import message.LoginMessage;
import message.Message;
import message.SendMessage;

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
            case "login":
                LoginMessage loginMessage = new LoginMessage();
                loginMessage.setType(CommandType.USER_LOGIN);
                loginMessage.setLogin(tokens[1]);
                loginMessage.setPass(tokens[2]);
                handler.send(loginMessage);
                break;
            case "send":
                SendMessage sendMessage = new SendMessage();
                sendMessage.setType(CommandType.MSG_SEND);
                sendMessage.setChatId(Long.valueOf(tokens[1]));
                sendMessage.setMessage(tokens[2]);
                handler.send(sendMessage);
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
    }

}
