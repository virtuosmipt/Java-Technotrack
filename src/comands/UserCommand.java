package comands;

import session.Session;

import java.util.Scanner;

/**
 * Created by a.borodin on 18.10.2015.
 */
public class UserCommand implements Command {

    @Override
    public void execute(Session session, String[] args){
        System.out.println("Enter new login!");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        session.getSessionUser().setName(name);
        System.out.println("You new login is:"+ session.getSessionUser().getName());
    }
}
