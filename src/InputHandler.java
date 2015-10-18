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
 * �������� ������ ������ ��������� �� ���� ������ �� ������������ � ������, ��� � ���� ������
 * <p>
 * ����� ������� � �����
 */
public class InputHandler {

    private Session session;

    private Map<String, Command> commandMap;

    public InputHandler(Session session, Map<String, Command> commandMap) {
        this.session = session;
        this.commandMap = commandMap;
    }

    public void handle(String line, Date dateTime) {
        // ��������� �� ���������� �������
        // ��� ������!

        if (line.startsWith("\\")) {
            boolean isCommand = false;
            String[] tokens = line.split(" ");


            // ������� ���������� �������, �� ��� �� ����� ��� �� �������,
            // � ��� ���� ����� execute()


                Command cmd = commandMap.get(tokens[0]);   //��������� ����������!!!!! �� �� �������


                cmd.execute(session, tokens);




        } else {
            String input;
            SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy hh:mm");
            input = format1.format(dateTime);
            input = input + ": ";
            input += line;
            input += line;
            System.out.println(">" + input);
            session.getSessionUser().userMessageStore.add(input);

        }
    }
}
