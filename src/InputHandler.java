/**
 * Created by a.borodin on 18.10.2015.
 */


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import comands.Command;
import session.Session;

/**
 * Основная задача класса принимать на вход данные от пользователя и решать, что с ними делать
 * <p>
 * Можно крутить в цикле
 */
public class InputHandler {

    private Session session;

    private Map<String, Command> commandMap;

    public InputHandler(Session session, Map<String, Command> commandMap) {
        this.session = session;
        this.commandMap = commandMap;
    }
    public boolean isCommand(String command){
    boolean isCom=false;
        for(Map.Entry<String,Command> entry: commandMap.entrySet()){

            if(entry.getKey().equals(command)){
                isCom=true;
            }
        }
        return isCom;
    }

    public void handle(String line, Date dateTime) {
        // проверяем на спецсимвол команды
        // Это пример!


            boolean isCommand = false;
            String[] tokens = line.split(" ");
                System.out.println(tokens[0]);
                isCommand= isCommand(tokens[0]);
        //System.out.println(isCommand);
            if (isCommand) {
                // Получим конкретную команду, но нам не важно что за команда,
                // у нее есть метод execute()


                Command cmd = commandMap.get(tokens[0]);   //ПРОПИСАТЬ ИСКЛЮЧЕНИЕ!!!!! на не команду


                cmd.execute(session, tokens);
            }



         else {
            String input;
            SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy hh:mm");
            input = format1.format(dateTime);
            input = input + ": ";
            input += line;

            System.out.println(">" + input);
            session.getSessionUser().userMessageStore.add(input);

        }
    }
}
