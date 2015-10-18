package comands;

import java.util.Map;

import session.Session;



/**
 * Вывести помощь
 */
public class HelpCommand implements Command {

    private Map<String, Command> commands;

    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void execute(Session session, String[] args) {
        for(Map.Entry<String,Command> entry: commands.entrySet()){

            System.out.println(entry.getKey());
        }


       //  System.out.println("Executing help");
        /*
        В простом случае просто выводим данные на консоль
        Если будем работать чере сеть, то команде придется передать также объект для работы с сетью

         */
    }
}
