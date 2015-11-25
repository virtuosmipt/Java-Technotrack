package ru.mail.track.jdbc;

import java.sql.*;

/**
 * Created by a.borodin on 23.11.2015.
 */
public class BDCreateTest {

    public static void main(String[] argv) throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");

        Connection c = DriverManager.getConnection("jdbc:postgresql://178.62.140.149:5432/virtuosmipt",
                "senthil", "ubuntu");

        Statement stmt;
        String sql;

        stmt = c.createStatement();
        sql = "CREATE TABLE IF NOT EXISTS User" +
                "(ID INT PRIMARY KEY     NOT NULL," +
                " USERNAME           TEXT    NOT NULL, " +
                " PASSWORD           TEXT    NOT NULL, " ;
               // " USERID        INT, ";
        stmt.executeUpdate(sql);
        stmt.close();
//        c.close();

        c.setAutoCommit(false);

        stmt = c.createStatement();
        sql = "INSERT INTO User (ID,USERNAME,PASSWORD) "
                + "VALUES (1, 'Paul', qwerty );";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO User (ID,USERNAME,PASSWORD) "
                + "VALUES (2, 'Allen', 2092853 );";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO User (ID,USERNAME,PASSWORD) "
                + "VALUES (3, 'Teddy',pass );";
        stmt.executeUpdate(sql);



        stmt.close();
        c.commit();
//        c.close();

        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM User;");
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("username");
           // int age = rs.getInt("age");
            String password = rs.getString("password");
          //  float salary = rs.getFloat("salary");
            System.out.println("ID = " + id);
            System.out.println("USERNAME = " + name);
            System.out.println("PASSWORD = " + password);
           // System.out.println("ADDRESS = " + address);
           // System.out.println("SALARY = " + salary);
            System.out.println();
        }
        rs.close();
        stmt.close();
//        c.close();

        /*stmt = c.createStatement();
        sql = "UPDATE COMPANY SET SALARY = 25000.00 WHERE ID=1;";
        stmt.executeUpdate(sql);
        c.commit();

        rs = stmt.executeQuery("SELECT * FROM COMPANY;");
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            String address = rs.getString("address");
            float salary = rs.getFloat("salary");
            System.out.println("ID = " + id);
            System.out.println("NAME = " + name);
            System.out.println("AGE = " + age);
            System.out.println("ADDRESS = " + address);
            System.out.println("SALARY = " + salary);
            System.out.println();
        }
        rs.close();
        stmt.close();
//        c.close();

        stmt = c.createStatement();
        sql = "DELETE FROM COMPANY WHERE ID=2;";
        stmt.executeUpdate(sql);
        c.commit();

        rs = stmt.executeQuery("SELECT * FROM COMPANY;");
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            String address = rs.getString("address");
            float salary = rs.getFloat("salary");
            System.out.println("ID = " + id);
            System.out.println("NAME = " + name);
            System.out.println("AGE = " + age);
            System.out.println("ADDRESS = " + address);
            System.out.println("SALARY = " + salary);
            System.out.println();
        }
        rs.close();
        stmt.close();
        c.close();

//        PreparedStatement prepStmnt = c.prepareStatement("INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
//                + "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );");
//        int parameterIndex = 234;
//        prepStmnt.setString(parameterIndex, "asd");
//        rs = prepStmnt.executeQuery();
*/
    }
}
