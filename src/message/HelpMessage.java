package message;

import comands.CommandType;

/**
 * Created by a.borodin on 09.11.2015.
 */
public class HelpMessage extends Message {

    public HelpMessage() {
        setType(CommandType.USER_HELP);
    }
}
