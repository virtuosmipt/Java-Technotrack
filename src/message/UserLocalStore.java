package message;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by a.borodin on 09.11.2015.
 */
public class UserLocalStore implements UserStore {

  public   ArrayList<User> userArrayList = new ArrayList<>();
    public AtomicLong id = new AtomicLong(0);

    @Override
    public User addUser(User user) {
       userArrayList.add(user);
        return user;
    }

    @Override
    public User getUser(String login, String pass) {
        for (User user: userArrayList){
            if(login!=null && login.equals(user.getName())){
                if(pass!=null && pass.equals(user.getPass())){
                    return user;
                }
            }
        }
        return null;
    }

    @Override
    public User getUserById(AtomicLong id) {
        for (User user: userArrayList){
            if(id.get()==user.getId()){
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean isUserExist(String name, String password) {
        boolean isUser=false;
        for(User user: userArrayList){
            if(name!=null && name.equals(user.getName())){
                if(user.getPass()!=null && password.equals(user.getPass())){
                    isUser=true;
                }
            }
        }
        return isUser;
    }

    @Override
    public boolean isUserExist(String name) {

        boolean isUser=false;
        for(User user: userArrayList){
            if(name!=null && name.equals(user.getName())){
                isUser=true;
            }
        }
        return isUser;
    }
}
