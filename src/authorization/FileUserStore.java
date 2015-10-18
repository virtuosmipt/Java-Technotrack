package authorization;

import session.User;

import java.util.ArrayList;

/**
 *
 */
public class FileUserStore implements UserStore {

    @Override
    public boolean isUserExist(String name) {
        boolean isExist=false;
        for (User user: userList){
            if(name !=null && user.getName().equals(name)){
                isExist=true;
            }

        }

        return isExist;
    }

    @Override
    public void addUser(User user) {
        userList.add(user);
    }

    @Override
    public User getUser(String name, String pass) {
        for (User user: userList){
            if (name!=null&& user.getName().equals(name)){
                if (pass!=null&&user.getPass().equals(pass)){
                    return user;
                }
            }
        }
        return null;
    }
}
