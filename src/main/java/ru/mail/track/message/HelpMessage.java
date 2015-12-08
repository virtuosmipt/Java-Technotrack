package ru.mail.track.message;

import java.util.ArrayList;

/**
 * Created by a.borodin on 09.11.2015.
 */
public class HelpMessage extends Message {
  public   ArrayList<String> listCommand = new ArrayList<String>();
    public HelpMessage() {

        listCommand.add("\\login <UserName> <Password> - Login User");
        listCommand.add("\\register <UserName> <Password> - User Registration");
        listCommand.add("\\help  - List Command");
        listCommand.add("\\user  <UserName> - Creat new UserName");
        listCommand.add("\\user_info  - Information about you");
        listCommand.add("\\user_info <UserId> - Information about Some User(ID)");
        listCommand.add("\\user_pass <Old_Pass> <New_Pass> - Create new Password");
        listCommand.add("\\chat_create <id_user> - Create new chat with User");
        listCommand.add("\\chat_list  - Your Chats");
        listCommand.add("\\chat_send <id_chat> <message> - Send Message in Chat");
        listCommand.add("\\chat_history <id_chat> - History message in Chat");
        listCommand.add("\\chat_find <id_chat> <word> - find word in Chat");
    }
}
