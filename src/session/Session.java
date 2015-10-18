package session;

/**
 * Класс содержит информацию о текущей сессии взаимодействия
 * Пока нам достаточно хранить юзера, возможно понадобится еще какое-то состояние
 */
public class Session {

    private User sessionUser;

    public Session() {
    }

    public User getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(User sessionUser) {
        this.sessionUser = sessionUser;
    }
}
