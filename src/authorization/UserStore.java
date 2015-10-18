package authorization;

import session.User;

import java.util.ArrayList;


/**
 * Хорошая идея спрятать реализацию хранилища за интерфейсом
 * Тогда практически везде мы будем работать с UserStore, не знаю как он реализован внутри
 *
 * Поменять код нам придется только в месте инициализации UserStore
 *
 */
public interface UserStore {
    // проверить, есть ли пользователь с таким именем
    // если есть, вернуть true

    ArrayList<User> userList = new ArrayList<>();

    boolean isUserExist(String name);

    // Добавить пользователя в хранилище
    void addUser(User user);

    // Получить пользователя по имени и паролю
    User getUser(String name, String pass);
}
