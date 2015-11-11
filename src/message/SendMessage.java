package message;

import comands.CommandType;

import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
public class SendMessage extends Message {

    private AtomicLong chatId;
    private String message;

    public SendMessage() {
        setType(CommandType.MSG_SEND);
    }

    public SendMessage(AtomicLong chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }

    public AtomicLong getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = new AtomicLong(chatId);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
