package ru.mail.track.jdbc.DAO;

import java.io.Serializable;
import java.sql.SQLException;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by a.borodin on 23.11.2015.
 */

/**
 * Унифицированный интерфейс управления персистентным состоянием объектов
 * @param <T> тип объекта персистенции
 * @param <PK> тип первичного ключа
 */
public interface GenericDAO <T, PK extends Serializable> {
    /** Создает новую запись и соответствующий ей объект */
    T create() throws PersistException;

    /** Создает новую запись, соответствующую объекту object */
     T persist(T object)  throws PersistException;

    /** Возвращает объект соответствующий записи с первичным ключом key или null */
     T getByPK(int key) throws PersistException;

     void update(T object) throws PersistException;

    /** Удаляет запись об объекте из базы данных */
     void delete(T object) throws PersistException;

    /** Возвращает список объектов соответствующих всем записям в базе данных */
     ArrayList<T> getAll() throws PersistException;

}
