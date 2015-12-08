package ru.mail.track.jdbc.DAO;

import ru.mail.track.jdbc.DAO.Exception.PersistException;

import java.io.Serializable;

import java.util.ArrayList;

/**
 * Created by a.borodin on 23.11.2015.
 */

/**
 * Унифицированный интерфейс управления персистентным состоянием объектов
 * @param <T> тип объекта персистенции
 */
public interface GenericDAO <T extends Identified> {

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
