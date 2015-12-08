package ru.mail.track.jdbc.DAO;
import ru.mail.track.jdbc.DAO.Exception.PersistException;

import javax.naming.Context;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by a.borodin on 24.11.2015.
 */
public interface DAOFactory<Context> {
    /** Возвращает подключение к базе данных */

    public interface DaoCreator<Context> {
        public GenericDAO create(Context context);
    }
    /** Возвращает подключение к базе данных */
    public Context getContext() throws PersistException;

    /** Возвращает объект для управления персистентным состоянием объекта */
    public GenericDAO getDao(Context context, Class dtoClass) throws PersistException;


}
