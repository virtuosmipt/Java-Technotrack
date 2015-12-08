package ru.mail.track.message;

import ru.mail.track.jdbc.DAO.Identified;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Chat implements Identified {

    private Long id;
    private String title;
    public Chat (){this.title="Java";}
    /**
     * Храним список идентификаторов
     */
    public String getTitle() { return title;}
    public void setTitle(String title) { this.title = title;}
    private List<Long> messageIds = new ArrayList<Long>();
    private ArrayList<Long> participantIds = new ArrayList<Long>();
    private ArrayList<String> messageByChat = new ArrayList<String>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getMessageIds() {
        return messageIds;
    }
    public ArrayList<String> getMessageByChat () {return messageByChat;}

    public void setMessageIds(List<Long> messageIds) {
        this.messageIds = messageIds;
    }

    public ArrayList<Long> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(ArrayList<Long> participantIds) {
        this.participantIds = participantIds;
    }

    public void addParticipant(Long id) {
        participantIds.add(id);
    }

    public void addMessage(Long id) {
        messageIds.add(id);
    }

}

