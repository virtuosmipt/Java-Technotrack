package ru.mail.track.jdbc.DAO;

import ru.mail.track.message.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by a.borodin on 24.11.2015.
 */
public class SqlUserDAO implements UserDAO {
    private Connection connection;

    public SqlUserDAO(Connection connection){
        this.connection = connection;
    }
    @Override
    public User Create() {
        return null;
    }

    @Override
    public User read(int key) throws SQLException {
        String sql = "SELECT * FROM User WHERE id =?;";
        User user = new User();
        try(PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setInt(1,key);
            ResultSet rs = stm.executeQuery();
            rs.next();
            user.setId(Long.valueOf(rs.getInt("id")) );
            user.setName(rs.getString("username"));
            user.setPass(rs.getString("password"));
        }
        return user;
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public ArrayList<User> getAll() throws SQLException {
        String sql = "SELECT * FROM User;";
        ArrayList<User> list = new ArrayList<>();
        try(PreparedStatement stm = connection.prepareStatement(sql)){
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(Long.valueOf(rs.getInt("id")) );
                user.setName(rs.getString("username"));
                user.setPass(rs.getString("password"));
                list.add(user);
            }
        }




        return list;
    }
}
