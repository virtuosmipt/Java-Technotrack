package comands;

import message.Message;
import net.MessageListener;
import session.Session;

import java.util.Map;

/**
 *
 */
public class CommandHandler implements MessageListener {



    Map<CommandType, Command> commands;

    public CommandHandler(Map<CommandType, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void onMessage(Session session, Message message) {
        Command cmd = commands.get(message.getType());

        cmd.execute(session, message);
    }
}
