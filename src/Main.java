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
import comands.*;
import session.IMessageStore;
import session.Session;


// ��� ���������. ���������� ������ �������� �������
public class Main {

    private static final String EXIT = "q|exit";

    public static void main(String[] args) {

        Map<String, Command> commands = new HashMap<>();


        // � ���� ������� �������� ���� � �����
        // �� ���� ������� ���������� ����
        Session session = new Session();

        // ���������� ���������� �������� � ����� �����
        UserStore userStore = new FileUserStore();


        AuthorizationService authService = new AuthorizationService(userStore,session);
        authService.startAuthorization();



        //������� �������
        Command loginCommand = new LoginCommand(authService);
        Command helpCommand = new HelpCommand(commands);
        Command historyCommand = new HistoryCommand();
        Command userCommand = new UserCommand();
        Command infoCommand = new InfoCommand();
        commands.put("\\login", loginCommand);
        commands.put("\\help", helpCommand);
        commands.put("\\history",historyCommand);
        commands.put("\\user",userCommand);
        commands.put("\\info",infoCommand);

        InputHandler handler = new InputHandler(session, commands);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();


            Date date = new Date();










            if (line != null && line.matches(EXIT)) {
                break;
            }
            handler.handle(line,date);
        }



    }
}