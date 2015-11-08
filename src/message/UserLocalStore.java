package message;

import java.util.ArrayList;

/**
 * Created by a.borodin on 09.11.2015.
 */
public class UserLocalStore implements UserStore {

    ArrayList<User> userArrayList = new ArrayList<>();

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
    public User getUserById(Long id) {
        for (User user: userArrayList){
            if(id==user.getId()){
                return user;
            }
        }
        return null;
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
