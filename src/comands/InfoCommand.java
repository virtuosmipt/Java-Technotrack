package comands;

import session.Session;

/**
 * Created by a.borodin on 20.10.2015.
 */
public class InfoCommand implements Command {
    @Override
    public void execute(Session session, String[] args){
        System.out.println("You login:" + session.getSessionUser().getName());
        System.out.println("You password:" + session.getSessionUser().getPass());

    }
}
