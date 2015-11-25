package ru.mail.track.jdbc.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by a.borodin on 24.11.2015.
 */
public class SqlFactoryDao implements DAOFactory {
    @Override
    public Connection getConnection() throws SQLException {


        Connection c = DriverManager.getConnection("jdbc:postgresql://178.62.140.149:5432/mydb",
                "senthil", "ubuntu");
        return c;
    }

    @Override
    public UserDAO getGroupDao(Connection connection) {
        return new SqlUserDAO(connection);
    }
}
