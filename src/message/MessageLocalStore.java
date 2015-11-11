package message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by a.borodin on 10.11.2015.
 */
public class MessageLocalStore implements MessageStore {
    //ArrayList<Long> chatList = new ArrayList<>();
    ArrayList<Chat> allChatList = new ArrayList<>();
    AtomicLong chatId = new AtomicLong(0);



    @Override
    public ArrayList<Long> getChatsByUserId(Long userId) {
        ArrayList<Long> mychat = new ArrayList<>();
        ArrayList<Long> participant ;
        for(Chat chat: allChatList){
            participant=chat.getParticipantIds();
            for(Long usersId: participant){
                if(usersId==userId){
                    mychat.add(chat.getId());
                }
            }
        }
        return mychat;
    }
    public Chat chatCreat(ArrayList<Long> userId){
        Chat chat = new Chat();
        chat.setId(chatId.incrementAndGet());
        System.out.println(chat.getId());
        for(Long lg: userId){
            chat.addParticipant(lg);
        }
        allChatList.add(chat);
        return chat;
    }
    public ArrayList<Long> getUsersByChat(Long idChat){
        ArrayList<Long> usersBychat;
        for(Chat chat:allChatList){
            if(chat.getId()==idChat){
                usersBychat=chat.getParticipantIds();
                return usersBychat;
            }
        }
        return null;
    }

    @Override
    public Chat getChatById(Long chatId) {

        for(Chat chat: allChatList){
            if(chat.getId()==chatId){
                return chat;
            }
        }
        return null;
    }

    @Override
    public List<Long> getMessagesFromChat(Long chatId) {
        return null;
    }

    @Override
    public Message getMessageById(Long messageId) {
        return null;
    }

    @Override
    public void addMessage(Long chatId, Message message) {

    }

    @Override
    public void addUserToChat(Long userId, Long chatId) {
        for(Chat chat: allChatList){
            if(chat.getId()==chatId){
                chat.addParticipant(userId);
            }
        }
    }
}
