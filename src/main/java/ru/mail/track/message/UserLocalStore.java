package ru.mail.track.message;

import ru.mail.track.jdbc.DAO.DAOFactory;
import ru.mail.track.jdbc.DAO.Exception.PersistException;
import ru.mail.track.jdbc.DAO.GenericDAO;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by a.borodin on 09.11.2015.
 */
public class UserLocalStore implements UserStore {

    public GenericDAO userDao;

public UserLocalStore(DAOFactory daoFactory){
    try {
        userDao = daoFactory.getDao(daoFactory.getContext(), User.class);
    } catch (PersistException e) {
        e.printStackTrace();
    }
}

    public   ArrayList<User> userArrayList = new ArrayList<User>();
    public AtomicLong id = new AtomicLong(0);

    @Override
    public User addUser(User user) {
        try {

            userDao.persist(user);
        } catch (PersistException e) {
            e.printStackTrace();
        }
       return user;

    }


    @Override
    public User getUser(String login, String pass) {
        ArrayList<User> list;
        try {
            list = userDao.getAll();
            for (User user : list) {
                if (user.getName().equals(login) && user.getPass().equals(pass)){
                    return user;
                }
            }
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUser(Long id) {
        try {
            User user = (User)userDao.getByPK(id.intValue());
            return user;
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public boolean isUserExist(String name, String password) {
        ArrayList<User> list;
        try {
            list = userDao.getAll();
            for (User user : list) {
                if (user.getName().equals(name) && user.getPass().equals(password)){
                    return true;
                }
            }
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isUserExist(String name) {


        ArrayList<User> list;
        try {
            list = userDao.getAll();
            for (User user : list) {
                if (user.getName().equals(name)){
                    return true;
                }
            }
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public void update(User user) {
        try {
            userDao.update(user);
        } catch (PersistException e) {
            e.printStackTrace();
        }
    }
}
