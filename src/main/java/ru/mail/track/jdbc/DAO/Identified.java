package ru.mail.track.jdbc.DAO;

import java.io.Serializable;

/**
 * Created by a.borodin on 24.11.2015.
 */
public interface Identified<PK extends Serializable> {

    /** Возвращает идентификатор объекта */
    public PK getId();
}