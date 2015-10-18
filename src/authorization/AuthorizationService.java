package authorization;


import session.*;


import java.util.Scanner;

public class AuthorizationService {

    private UserStore userStore;
    private Session session;
    //Session session = new Session();

    public AuthorizationService(UserStore userStore,Session session) {
        this.userStore = userStore;
        this.session=session;
    }

     public void startAuthorization() {
        if (isLogin()) {
            login();
        }
      else{
            creatUser();
        }
    }

    public User login() {
//            1. Ask for name
//            2. Ask for password
//            3. Ask UserStore for user:  userStore.getUser(name, pass)


        System.out.println("Enter your Name!");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.next();

        if (userStore.isUserExist(name)) {

            System.out.println("Enter your Password!");
            String pass = scanner.next();

            if (userStore.getUser(name, pass) != null) {
                User newSomeUser = new User(name,pass);

                session.setSessionUser(newSomeUser);
                System.out.println("Congrats!");
                return newSomeUser;
            } else {
                System.out.println(" Password is not True:(, try to join !");
                login();
            }
        } else {
            System.out.println("You dont't find in our base,Please write \\login");
           startAuthorization();
           // return null;
        }


        return null;

    }

    public User creatUser() {
        // 1. Ask for name
        // 2. Ask for pass
        // 3. Add user to UserStore: userStore.addUser(user)


        System.out.println("Enter your Name!");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.next();
        if (userStore.isUserExist(name)) {
            System.out.println("name is busy, write smh another");
            creatUser();
        }

        System.out.println("Enter your Password!");
        String pass = scanner.next();
        User newUser = new User(name, pass);
        userStore.addUser(newUser);


        System.out.println("Please, join to us!");
        login();
        return newUser;


    }

    private boolean isLogin() {

        System.out.println("Do you want Autorization or Join? if you want Autorizatio,press a , else prees j!");
        Scanner scanner = new Scanner(System.in);
        String auto = scanner.next();
        if (auto.equals("a") == false && auto.equals("j") == false) {
            System.out.println("Invalid input, please try again!");
            isLogin();
        }

        if (auto != null && auto.equals("j")) {
            return true;
        }


        if (auto != null && auto.equals("a")) {

            return false;
        }

        return false;
    }
}
