package ru.mail.track.message;

import ru.mail.track.jdbc.DAO.Identified;

/**
 * Created by a.borodin on 04.12.2015.
 */
public class UserChatRelation implements Identified{
    private Long userChatRelationId;
    private Long userId;
    private Long chatId;

    public UserChatRelation(){

    }

    public UserChatRelation(Long chatId, Long userId){
        this.userId = userId;
        this.chatId = chatId;
    }

    @Override
    public Long getId() {
        return userChatRelationId;
    }

    @Override
    public void setId(Long id) {
        userChatRelationId = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
