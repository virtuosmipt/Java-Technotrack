package ru.mail.track.message;

import ru.mail.track.jdbc.DAO.DAOFactory;
import ru.mail.track.jdbc.DAO.Exception.PersistException;
import ru.mail.track.jdbc.DAO.GenericDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by a.borodin on 10.11.2015.
 */
public class MessageLocalStore implements MessageStore {

    private GenericDAO messageDao, chatDao, userDao, userChatDao;
    //ArrayList<Long> chatList = new ArrayList<>();
    ArrayList<Chat> allChatList = new ArrayList<Chat>();
    AtomicLong chatId = new AtomicLong(0);



    public MessageLocalStore(DAOFactory daoFactory){
        try {
            messageDao = daoFactory.getDao(daoFactory.getContext(), ChatMessage.class);
            chatDao = daoFactory.getDao(daoFactory.getContext(), Chat.class);
            userDao = daoFactory.getDao(daoFactory.getContext(), User.class);
            userChatDao = daoFactory.getDao(daoFactory.getContext(), UserChatRelation.class);
        } catch (PersistException e) {
            e.printStackTrace();
        }
    }


    @Override
    public ArrayList<Long> getParticipantByChatId(Long chatId) {
        ArrayList<Long> participant = new ArrayList<>();
        try {
            List<UserChatRelation> userChatRelations = userChatDao.getAll();
            for (UserChatRelation userChatRelation : userChatRelations){
                if (userChatRelation.getChatId() == chatId){
                    participant.add(userChatRelation.getUserId());
                }
            }
        } catch (PersistException e){
            e.printStackTrace();
        }
        return participant;
    }

    @Override
    public ArrayList<Long> getChatsByUserId(Long userId) {
        ArrayList<Long> chats = new ArrayList<>();
        try {
            ArrayList<UserChatRelation> userChatRelations = userChatDao.getAll();
            for (UserChatRelation userChatRelation : userChatRelations){
                if (userChatRelation.getUserId() == userId){
                    chats.add(userChatRelation.getChatId());
                }
            }
        } catch (PersistException e){
            e.printStackTrace();
        }
        return chats;
    }
    public Chat chatCreat(Chat chat){
        try {
            Chat r = (Chat)chatDao.persist(chat);
            System.out.println(r.getId());
            Long t =r.getId() ;
            List<Long> usersIds = chat.getParticipantIds();
            for (Long aLong : usersIds) {
                UserChatRelation userChatRelation = new UserChatRelation(t, aLong);
                userChatDao.persist(userChatRelation);
            }
            return r;
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Chat getChatById(Long chatId) {

        try {
            return (Chat)chatDao.getByPK(chatId.intValue());
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Long> getMessagesFromChat(Long chatId) {
        ArrayList<Long> messages = new ArrayList<>();
        try {
            List<ChatMessage> allMessages = messageDao.getAll();
            for (ChatMessage message : allMessages) {
                if (message.getChatId() == chatId){
                    messages.add(message.getId());
                }
            }
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    public ChatMessage getMessageById(Long messageId) {
        try {
            return (ChatMessage)messageDao.getByPK(messageId.intValue());
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addMessage( ChatMessage message) {
        try {
            messageDao.persist(message);
        } catch (PersistException e) {
            e.printStackTrace();
        }
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
