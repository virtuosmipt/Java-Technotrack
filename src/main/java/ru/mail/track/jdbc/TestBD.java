package ru.mail.track.jdbc;
import java.sql.*;

/**
 * Created by a.borodin on 23.11.2015.
 */
public class TestBD {
    public static void main(String[] argv) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        Connection c = DriverManager.getConnection("jdbc:postgresql://178.62.140.149:5432/virtuosmipt",
                "senthil", "ubuntu");

        Statement stmt;
        String sql;
        sql = "INSERT INTO User VALUES ('Paul',2, 'paul')";

        stmt = c.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
        c.commit();
        c.close();



       /* stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM User;");
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("username");
            String password = rs.getString("password");

            System.out.println("ID = " + id);
            System.out.println("NAME = " + name);
            System.out.println("PASSWORD = " + password);
            System.out.println();
        }
        rs.close();
        stmt.close();

       // dispResultSet (rs);


        c.close();
    }

    private static void dispResultSet (ResultSet rs)
            throws SQLException
    {
        int i;

        // Получить ResultSetMetaData. Он нужен для
        // получения загловков колонок

        ResultSetMetaData rsmd = rs.getMetaData ();

        // Взять количество колонок в наборе данных

        int numCols = rsmd.getColumnCount ();

        // Показать шапку

        for (i=1; i<=numCols; i++) {
            if (i > 1) System.out.print(",");
            System.out.print(rsmd.getColumnLabel(i));

        }
        System.out.println("");

        // Показать все данные вплоть до конца набора данных

        boolean more = rs.next ();
        while (more) {

            // Для каждой колонки в цикле: получить
            // ее значение и показать его

            for (i=1; i<=numCols; i++) {
                if (i > 1) System.out.print(",");
                System.out.print(rs.getString(i));
            }
            System.out.println("");

            // Передвинуться на следующую строку набора данных

            more = rs.next ();
        }
    }

*/

    }
}
