package ru.mail.track.jdbc.DAO;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by a.borodin on 24.11.2015.
 */
public interface DAOFactory {
    /** Возвращает подключение к базе данных */
   Connection getConnection() throws SQLException;

    /** Возвращает объект для управления персистентным состоянием объекта User */
    UserDAO getGroupDao(Connection connection);

}
