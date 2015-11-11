package net;



import comands.CommandType;
import message.*;

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
            case ERROR_LOGIN:
                ErrorLoginMessage errorLoginMessage = new ErrorLoginMessage();
                errorLoginMessage.setErrorMessage(tokens[1]);
                return errorLoginMessage;
            case USER_REGISTER:
                RegisterMessage registerMessage= new RegisterMessage();
                registerMessage.setLogin(tokens[1]);
                registerMessage.setPass(tokens[2]);
                return registerMessage;
            case USER_NAME:
                UserMessage userMessage= new UserMessage();
                userMessage.setLogin(tokens[1]);
                return userMessage;
            case USER_PASS:
                PassMessage passMessage = new PassMessage();
                passMessage.setOldPass(tokens[1]);
                passMessage.setNewPass(tokens[2]);
                return passMessage;
            case CHAT_CREAT:
                ChatCreatMessage chatCreatMessage = new ChatCreatMessage();
                for(int i=1;i<tokens.length;i++){
                    chatCreatMessage.userIdList.add(Long.valueOf(tokens[i]));
                }
                return chatCreatMessage;
            case CHAT_LIST:
                ChatListMessage chatListMessage = new ChatListMessage();
                if(tokens.length>1){
                    for (int i=1;i<tokens.length;i++){
                        chatListMessage.chatsId.add(Long.valueOf(tokens[i]));
                    }
                }
                return chatListMessage;
            case CHAT_FIND:
                ChatFindMessage chatFindMessage = new ChatFindMessage();
                chatFindMessage.setStringFind(tokens[2]);
                chatFindMessage.setIdChat(Long.valueOf(tokens[1]));
                return chatFindMessage;
            case CHAT_FIND_OUT:
                ChatFindMessage chatFindMessage1 = new ChatFindMessage();
                if(tokens.length>1){
                chatFindMessage1.findingStringList.add("this String don't find in this ChatId");
                }
                else {
                    for(int i=1;i<tokens.length;i++){
                        chatFindMessage1.findingStringList.add(tokens[i]);
                    }
                }
                return chatFindMessage1;
            case CHAT_SEND:
                ChatSendMessage chatSendMessage = new ChatSendMessage();
                for(int i=1;i<tokens.length-1;i++){
                    chatSendMessage.chatsId.add(Long.valueOf(tokens[i]));
                }
                chatSendMessage.setMessages(tokens[tokens.length-1]);
                return chatSendMessage;
            case USER_INFORMATION:
                UserInfoMessage userInfoMessage = new UserInfoMessage();
                if(tokens.length>1){
                    try {

                        Long id = Long.valueOf(tokens[1]);
                        userInfoMessage.setId(id);
                        return userInfoMessage;


                    } catch (NumberFormatException e) {
                        System.err.println("Неверный формат строки!");
                    }
                }
                else {
                    return userInfoMessage;
                }

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
            case USER_HELP:
                break;
            case USER_PASS:
                PassMessage passMessage = (PassMessage) msg;
                builder.append(passMessage.getOldPass()).append(DELIMITER);
                builder.append(passMessage.getNewPass()).append(DELIMITER);
                break;
            case USER_REGISTER:
                RegisterMessage registerMessage = (RegisterMessage) msg;
                builder.append(registerMessage.getLogin()).append(DELIMITER);
                builder.append(registerMessage.getPass()).append(DELIMITER);
                break;
            case ERROR_LOGIN:
                ErrorLoginMessage errorLoginMessage = (ErrorLoginMessage) msg;
                builder.append(errorLoginMessage.getErrorLogin()).append(DELIMITER);
                break;
            case USER_INFO:
                InfoMessage infoMessage = (InfoMessage) msg;
                builder.append(infoMessage.getStringInfo()).append(DELIMITER);
                break;
            case USER_NAME:
                UserMessage userMessage = (UserMessage) msg;
                builder.append(userMessage.getLogin()).append(DELIMITER);
                break;
            case USER_INFORMATION:
                UserInfoMessage userInfoMessage = (UserInfoMessage) msg;
                if(userInfoMessage.getId()!=null){
                    builder.append(userInfoMessage.getId()).append(DELIMITER);
                }
                break;
            case CHAT_CREAT:
                ChatCreatMessage chatCreatMessage = (ChatCreatMessage) msg;
                for(Long lg: chatCreatMessage.userIdList){
                    builder.append(lg).append(DELIMITER);
                }
                break;
            case CHAT_LIST:
                ChatListMessage chatListMessage = (ChatListMessage) msg;
                if(chatListMessage.chatsId.isEmpty()==false){
                    for(Long lg: chatListMessage.chatsId){
                        builder.append(lg).append(DELIMITER);
                    }
                }
                break;
            case CHAT_FIND:
                ChatFindMessage chatFindMessage = (ChatFindMessage) msg;
                    builder.append(chatFindMessage.getIdChat()).append(DELIMITER);
                    builder.append(chatFindMessage.getStringFind()).append(DELIMITER);
                break;
            case CHAT_FIND_OUT:
                ChatFindMessage chatFindMessage1 =(ChatFindMessage) msg;
                    for(String str: chatFindMessage1.findingStringList){
                        builder.append(str).append(DELIMITER);
                    }
                break;
            case CHAT_SEND:
                ChatSendMessage chatSendMessage = (ChatSendMessage) msg;
                for(Long lg: chatSendMessage.chatsId){
                    builder.append(lg).append(DELIMITER);
                }
                builder.append(chatSendMessage.getMessages()).append(DELIMITER);
                break;


            default:
                throw new RuntimeException("Invalid type: " + type);


        }

        return builder.toString().getBytes();
    }



}
