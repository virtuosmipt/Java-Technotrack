package message;

import comands.CommandType;

import java.util.ArrayList;

/**
 * Created by a.borodin on 09.11.2015.
 */
public class HelpMessage extends Message {
  public   ArrayList<String> listCommand = new ArrayList<>();
    public HelpMessage() {

        listCommand.add("\\login <UserName> <Password> - Login User");
        listCommand.add("\\register <UserName> <Password> - User Registration");
        listCommand.add("\\help  - List Command");
        listCommand.add("\\user  <UserName> - Creat new UserName");
        listCommand.add("\\user_info  - Information about you");
        listCommand.add("\\user_info <UserId> - Information about Some User(ID)");
        listCommand.add("\\user_pass <Old_Pass> <New_Pass> - Creat new Password");
        listCommand.add("\\chat_creat <id_user>");



    }
}
