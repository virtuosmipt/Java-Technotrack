/**
 * Created by a.borodin on 18.10.2015.
 */



import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

import authorization.AuthorizationService;
import authorization.FileUserStore;
import authorization.UserStore;
import com.sun.javafx.scene.layout.region.Margins;
import comands.Command;
import comands.HelpCommand;
import comands.HistoryCommand;
import comands.LoginCommand;
import session.IMessageStore;
import session.Session;


// Это псевдокод. Показывает работу паттерна Команда
public class Main {

    private static final String EXIT = "q|exit";

    public static void main(String[] args) {

        Map<String, Command> commands = new HashMap<>();


        // В этом объекте хранится инфа о сесии
        // то есть текущее стостояние чата
        Session session = new Session();

        // Реализация интерфейса задается в одном месте
        UserStore userStore = new FileUserStore();


        AuthorizationService authService = new AuthorizationService(userStore,session);
        authService.startAuthorization();



        //Создаем команды
        Command loginCommand = new LoginCommand(authService);
        Command helpCommand = new HelpCommand(commands);
        Command historyCommand = new HistoryCommand();
        commands.put("\\login", loginCommand);
        commands.put("\\help", helpCommand);
        commands.put("\\history",historyCommand);

        InputHandler handler = new InputHandler(session, commands);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.next();


            Date date = new Date();










            if (line != null && line.matches(EXIT)) {
                break;
            }
            handler.handle(line,date);
        }



    }
}