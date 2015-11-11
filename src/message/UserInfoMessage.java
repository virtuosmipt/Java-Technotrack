package message;

import comands.CommandType;

/**
 * Created by a.borodin on 10.11.2015.
 */
public class UserInfoMessage extends Message {
    private Long id;
    public UserInfoMessage() {
        setType(CommandType.USER_INFORMATION);
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id){this.id=id;}
}