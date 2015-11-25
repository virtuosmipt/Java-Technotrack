package ru.mail.track.comands;

import ru.mail.track.message.Message;
import ru.mail.track.net.MessageListener;
import ru.mail.track.session.Session;

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
