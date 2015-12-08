package ru.mail.track.message;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Хранилище информации о пользователе
 */
public interface UserStore {

    /**
     * Добавить пользователя в хранилище
     * Вернуть его же
     */
    User addUser(User user);

    /**
     *
     * Получить пользователя по логину/паролю
     */
    User getUser(String login, String pass);
    User getUser (Long id);
    void update(User user);

    /**
     *
     * Получить пользователя по id, например запрос информации/профиля
     */


    boolean isUserExist(String name);
    boolean isUserExist(String name,String password);
}
