package net;

import comands.*;
import message.MessageStore;
import message.MessageStoreStub;
import message.UserStore;
import message.UserLocalStore;
import message.UserStoreStub;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
public class ThreadedServer {

    public static final int PORT = 19000;

    private volatile boolean isRunning;
    private Map<Long, ConnectionHandler> handlers = new HashMap<>();
    private AtomicLong internalCounter = new AtomicLong(0);
    private ServerSocket sSocket;
    private Protocol protocol;
    private SessionManager sessionManager;
    private CommandHandler commandHandler;


    public ThreadedServer(Protocol protocol, SessionManager sessionManager, CommandHandler commandHandler) {
        try {
            this.protocol = protocol;
            this.sessionManager = sessionManager;
            this.commandHandler = commandHandler;
            sSocket = new ServerSocket(PORT);
            sSocket.setReuseAddress(true);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        Protocol protocol = new StringProtocol();
        SessionManager sessionManager = new SessionManager();

        UserLocalStore userLocalStore = new UserLocalStore();
        MessageStore messageStore = new MessageStoreStub();

        Map<CommandType, Command> cmds = new HashMap<>();
        cmds.put(CommandType.USER_LOGIN, new LoginCommand(userLocalStore, sessionManager));
        cmds.put(CommandType.MSG_SEND, new SendCommand(sessionManager, messageStore));
        cmds.put(CommandType.USER_HELP, new HelpCommand(cmds));
        cmds.put(CommandType.USER_REGISTER,new RegisterCommand(userLocalStore,sessionManager));
        CommandHandler handler = new CommandHandler(cmds);


        ThreadedServer server = new ThreadedServer(protocol, sessionManager, handler);

        try {
            server.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startServer() throws Exception {


        isRunning = true;
        while (isRunning) {
            Socket socket = sSocket.accept();


            ConnectionHandler handler = new SocketConnectionHandler(protocol, sessionManager.createSession(), socket);
            handler.addListener(commandHandler);


            handlers.put(internalCounter.incrementAndGet(), handler);
            Thread thread = new Thread(handler);

            thread.start();
           // System.out.println("server start response");
        }
    }

    public void stopServer() {
        isRunning = false;
        for (ConnectionHandler handler : handlers.values()) {
            handler.stop();
        }
    }

}
