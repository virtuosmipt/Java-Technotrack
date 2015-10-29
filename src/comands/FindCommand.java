package comands;

import session.Session;

import java.util.Map;

/**
 * Created by a.borodin on 20.10.2015.
 */
public class FindCommand implements Command {

    @Override
    public void execute(Session session, String[] args) {
        boolean isFind=false;
        String []tokens;
        for (String str: session.getSessionUser().userMessageStore){
             tokens = str.split(" ");
            for(String str1: tokens) {
                if (args[1] != null && args[1].equals(str1)) {
                    isFind=true;
                    System.out.println(args[1] + " find in this:");
                    System.out.println(str);
                }
            }
        }
        if (isFind==false){
            System.out.println("Don't find this word");
        }

    }
}
