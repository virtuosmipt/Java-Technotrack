package ru.mail.track.jdbc.DAO.DAOSql;
import ru.mail.track.jdbc.DAO.AbstractJDBCDao;
import ru.mail.track.jdbc.DAO.Exception.PersistException;
import ru.mail.track.message.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by a.borodin on 24.11.2015.
 */
public class SqlUserDAO extends AbstractJDBCDao<User> {
    private Connection connection;

    private class PersistUser extends User{
        public void setId(Long id){
            super.setId(id);
        }
    }

    @Override
    public String getSelectQuery() {
        return  "SELECT username, id, password FROM users ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO users (username, password) \n" +
                "VALUES (?, ?);";
    }

    @Override
    public String getLastObject(){ return  "SELECT * FROM " + getSeqClass()+ " ORDER BY id DESC LIMIT 1;";}

    @Override
    public String getUpdateQuery() {
        return "UPDATE users \n" +
                "SET username = ?, password = ? \n" +
                "WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM users WHERE id= ?;";
    }

    public SqlUserDAO(Connection connection){
        super(connection);
    }

    @Override
    protected ArrayList<User> parseResultSet(ResultSet rs) throws PersistException {
        ArrayList<User> result = new ArrayList<>();
        try {
            while (rs.next()) {
                PersistUser user = new PersistUser();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("username"));
                user.setPass(rs.getString("password"));
                result.add(user);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User object) throws PersistException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getPass());
            statement.setLong(3, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User object) throws PersistException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getPass());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
    @Override
    protected String getSequence() {
        return "user_id_seq";
    }

    @Override
    protected String getSeqClass() {
        return "users";
    }

}
