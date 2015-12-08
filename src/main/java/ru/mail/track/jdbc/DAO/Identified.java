package ru.mail.track.jdbc.DAO;

import java.io.Serializable;

/**
 * Created by a.borodin on 24.11.2015.
 */
public interface Identified {
    /**Возвращает идентификатор объекта**/
    Long getId();
    void setId(Long id);
}