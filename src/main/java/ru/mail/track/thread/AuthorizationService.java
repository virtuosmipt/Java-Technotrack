package ru.mail.track.thread;


import ru.mail.track.message.User;
import ru.mail.track.message.UserLocalStore;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;

public class AuthorizationService {

    private UserLocalStore userLocalStore;
    public AtomicLong id = new AtomicLong(0);

    public AuthorizationService(UserLocalStore userLocalStore) {
        this.userLocalStore = userLocalStore;
    }

  public   void startAuthorization() {
        if (isLogin()) {
            login();
        }
        else{
            creatUser();
        }
    }

    public User login() {


        System.out.println("Enter your Name!");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.next();

        if (userLocalStore.isUserExist(name)) {

            System.out.println("Enter your Password!");
            String pass = scanner.next();

            if (userLocalStore.getUser(name, pass) != null) {
                User newSomeUser = new User(name,pass);

                //session.setSessionUser(newSomeUser);
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

//            1. Ask for name
//            2. Ask for password
//            3. Ask UserStore for user:  userStore.getUser(name, pass)


    }

    public User creatUser() {


        System.out.println("Enter your Name!");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.next();
        if (userLocalStore.isUserExist(name)) {
            System.out.println("name is busy, write smh another");
            creatUser();
        }

        System.out.println("Enter your Password!");
        String pass = scanner.next();
        User newUser = new User(name, pass);
        //newUser.setId(userLocalStore.idUsers++);

        userLocalStore.addUser(newUser);


        System.out.println("Please, join to us!");
        login();
        return newUser;
        // 1. Ask for name
        // 2. Ask for pass
        // 3. Add user to UserStore: userStore.addUser(user)


    }
    public User creatUser(String name,String pass){
        if (userLocalStore.isUserExist(name)) {
            System.out.println("name is busy, write smh another");
            creatUser(name, pass);
        }
        User newUser = new User(name, pass);
        newUser.setId(id.get());
        id.incrementAndGet();

        userLocalStore.addUser(newUser);
        return newUser;
    }

    public boolean isLogin() {
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
