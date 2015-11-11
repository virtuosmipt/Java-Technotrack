package message;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Chat {

    private Long id;
    /**
     * Храним список идентификаторов
     */

    private List<Long> messageIds = new ArrayList<>();
    private ArrayList<Long> participantIds = new ArrayList<>();
    private ArrayList<String> messageByChat = new ArrayList<>();

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

