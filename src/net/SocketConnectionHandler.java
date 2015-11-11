package net;

import message.Message;
import message.SendMessage;
import session.Session;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс работающий с сокетом, умеет отправлять данные в сокет
 * Также слушает сокет и рассылает событие о сообщении всем подписчикам (асинхронность)
 */
public class SocketConnectionHandler implements ConnectionHandler {



    // подписчики
    private List<MessageListener> listeners = new ArrayList<>();
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private Protocol protocol;
    private Session session;
   // ObjectInputStream ois;
    //ObjectOutputStream oos;

    public SocketConnectionHandler(Protocol protocol, Session session, Socket socket) throws IOException {
        this.protocol = protocol;
        this.socket = socket;
        this.session = session;
        session.setConnectionHandler(this);
        in = socket.getInputStream();
        out = socket.getOutputStream();
        // ois = new ObjectInputStream(socket.getInputStream());
        // oos = new ObjectOutputStream(socket.getOutputStream());

    }

    @Override
    public void send(Message msg) throws IOException {


        // TODO: здесь должен быть встроен алгоритм кодирования/декодирования сообщений
        // то есть требуется описать протокол
        out.write(protocol.encode(msg));
        out.flush();

      //  oos.writeObject(msg);
      //  oos.flush();

    }

    // Добавить еще подписчика
    @Override
    public void addListener(MessageListener listener) {
        listeners.add(listener);
    }


    // Разослать всем
    public void notifyListeners(Session session, Message msg) {
        listeners.forEach(it -> it.onMessage(session, msg));
    }

    @Override
    public void run() {

        final byte[] buf = new byte[1024 * 64];
        while (!Thread.currentThread().isInterrupted()) {
            try {
                int read = in.read(buf);
                 if (read > 0) {
                    // System.out.println("zashel");
                    Message msg = protocol.decode(Arrays.copyOf(buf, read));
                   // Message msg = (Message) ois.readObject();
                    msg.setSender(session.getId());
                   // System.out.println("Server send you: "+  ((SendMessage) msg).getMessage() );
                    // Уведомим всех подписчиков этого события
                    notifyListeners(session, msg);
                }
            } catch (Exception e) {

                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void stop() {
        Thread.currentThread().interrupt();
    }
}


