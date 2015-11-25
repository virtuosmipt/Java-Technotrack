package ru.mail.track.jdbc.DAO;

import ru.mail.track.message.User;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by a.borodin on 24.11.2015.
 */
public interface UserDAO {

     User Create();

     User read(int key)  throws SQLException;

     void update(User user);

     void delete(User user);

    ArrayList<User> getAll() throws SQLException;




}
