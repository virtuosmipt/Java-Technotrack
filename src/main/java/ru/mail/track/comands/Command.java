package ru.mail.track.comands;

import ru.mail.track.message.Message;
import ru.mail.track.session.Session;

/**
 * Интерфейс для всех команд
 *
 *  То есть, даже имея возможность определить здесь абстрактный метод execute() я предпочту интерфейс
 * потому что интерфейс определяет поведение (свойство)
 *
 * А цель абстрактного класса - переиспользование кода
 */
public interface Command {


    /**
     * Здесь можно возвращать результат, подумайте как лучше сделать
     * результат желательно инкапсулировать в неком объекте Result
     *
     * В качестве пример оставлю void
     */
    void execute(Session session, Message message);

}
