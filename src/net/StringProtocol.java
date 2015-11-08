package net;



import comands.CommandType;
import message.LoginMessage;
import message.Message;
import message.SendMessage;

/**
 *
 */

public class StringProtocol implements Protocol {



    public static final String DELIMITER = ";";

    @Override
    public Message decode(byte[] bytes) {
        String str = new String(bytes);

        String[] tokens = str.split(DELIMITER);
        CommandType type = CommandType.valueOf(tokens[0]);
        switch (type) {
            case USER_LOGIN:
                LoginMessage loginMessage = new LoginMessage();
                loginMessage.setLogin(tokens[1]);
                loginMessage.setPass(tokens[2]);
                return loginMessage;
            case MSG_SEND:
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(Long.valueOf(tokens[1]));
                sendMessage.setMessage(tokens[2]);
                return sendMessage;
            default:
                //throw new RuntimeException("Invalid type: " + type);
                SendMessage sendMsg = new SendMessage();
                sendMsg.setMessage(str);
                return sendMsg;
        }
    }

    @Override
    public byte[] encode(Message msg) {
        StringBuilder builder = new StringBuilder();
        CommandType type = msg.getType();
        builder.append(type).append(DELIMITER);
        switch (type) {
            case USER_LOGIN:
                LoginMessage loginMessage = (LoginMessage) msg;
                builder.append(loginMessage.getLogin()).append(DELIMITER);
                builder.append(loginMessage.getPass()).append(DELIMITER);
                break;
            case MSG_SEND:
                SendMessage sendMessage = (SendMessage) msg;
                builder.append(sendMessage.getChatId()).append(DELIMITER);
                builder.append(sendMessage.getMessage()).append(DELIMITER);
                break;
            case  USER_HELP:

                break;

            default:
                throw new RuntimeException("Invalid type: " + type);


        }

        return builder.toString().getBytes();
    }



}
