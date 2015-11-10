package message;


import java.util.concurrent.atomic.AtomicLong;

public class User {
    private AtomicLong userId = new AtomicLong(0);
    private String name;
    private String pass;

    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public Long getId() {
        return userId.get();
    }

    public void setId(Long id) {
        this.userId = new AtomicLong(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + userId +
                ", name='" + name + '\'' +
                '}';
    }
}
