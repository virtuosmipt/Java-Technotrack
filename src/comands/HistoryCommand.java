package comands;

import session.Session;

import java.util.ArrayList;

/**
 * Created by a.borodin on 18.10.2015.
 */
public class HistoryCommand implements Command {



    @Override
    public void execute(Session session, String[] args){

       /* int n = 0;
        String str2 = args[1];
        try {
            n = Integer.parseInt(str2);

        } catch (NumberFormatException e) {
            System.err.println("It's not String!");
        }
*/

            if(args.length>1){
                int n = 0;
                String str2 = args[1];
                try {
                    n = Integer.parseInt(str2);

                } catch (NumberFormatException e) {
                    System.err.println("It's not String!!!");
                }

                if(n<=session.getSessionUser().userMessageStore.size()) {
                   // System.out.println(session.getSessionUser().userMessageStore.size());

                    for (int i = session.getSessionUser().userMessageStore.size()-1; i >= session.getSessionUser().userMessageStore.size() - n; i--) {
                        System.out.println(i);
                        System.out.println(session.getSessionUser().userMessageStore.get(i));

                    }
                }
                else{
                    System.out.println("n greater than your message");
                }

            }
        else {
               // System.out.println("UPS");
                for (int i = 0; i < (session.getSessionUser().userMessageStore.size()); i++) {
                    System.out.println(session.getSessionUser().userMessageStore.get(i));

                }
            }

    }

}
