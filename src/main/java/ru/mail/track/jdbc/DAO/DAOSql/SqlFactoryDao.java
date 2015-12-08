package ru.mail.track.jdbc.DAO.DAOSql;

import ru.mail.track.jdbc.DAO.DAOFactory;
import ru.mail.track.jdbc.DAO.Exception.PersistException;
import ru.mail.track.jdbc.DAO.GenericDAO;
import ru.mail.track.message.Chat;
import ru.mail.track.message.ChatMessage;
import ru.mail.track.message.User;
import ru.mail.track.message.UserChatRelation;
import org.postgresql.ds.PGPoolingDataSource;

import javax.naming.Context;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by a.borodin on 24.11.2015.
 *
 *
 *
 * Connection c = DriverManager.getConnection("jdbc:postgresql://178.62.140.149:5432/virtuosmipt",
 "senthil", "ubuntu");
 */
public class SqlFactoryDao implements DAOFactory<Connection> {
    private PGPoolingDataSource source;
    private String user = "senthil";//Логин пользователя
    private String password = "ubuntu";//Пароль пользователя
   // private String url = "jdbc:postgresql://178.62.140.149:5432/virtuosmipt";//URL адрес
     private String driver = "org.postgresql.Driver";
    private Map<Class, DaoCreator> creators;

    @Override
    public Connection getContext() throws PersistException {
        Connection connection = null;
        try {
            connection = source.getConnection();
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return  connection;
    }

    @Override
    public GenericDAO getDao(Connection connection, Class dtoClass) throws PersistException {
        DaoCreator creator = creators.get(dtoClass);
        if (creator == null) {
            throw new PersistException("Dao object for " + dtoClass + " not found.");
        }
        return creator.create(connection);
    }


    public SqlFactoryDao(){
        try {
            Class.forName(driver);//Регистрируем драйвер
            source = new PGPoolingDataSource();
            source.setServerName("178.62.140.149");
            source.setDatabaseName("virtuosmipt");
            source.setUser(user);
            source.setPassword(password);
            source.setMaxConnections(5);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        creators = new HashMap<Class, DaoCreator>();
        creators.put(User.class, new DaoCreator<Connection>() {
            @Override
            public GenericDAO create(Connection connection) {
                return new SqlUserDAO(connection);
            }
        });
        creators.put(Chat.class, new DaoCreator<Connection>() {
            @Override
            public GenericDAO create(Connection connection) {
                return new SqlChatDao(connection);
            }
        });
        creators.put(ChatMessage.class, new DaoCreator<Connection>() {
            @Override
            public GenericDAO create(Connection connection) {
                return new SqlMessageDao(connection);
            }
        });
        creators.put(UserChatRelation.class, new DaoCreator<Connection>() {
            @Override
            public GenericDAO create(Connection connection) {
                return new SqlUserChatDao(connection);
            }
        });
    }



}
