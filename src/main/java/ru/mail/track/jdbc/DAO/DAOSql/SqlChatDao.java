package ru.mail.track.jdbc.DAO.DAOSql;

import ru.mail.track.jdbc.DAO.AbstractJDBCDao;
import ru.mail.track.jdbc.DAO.Exception.PersistException;
import ru.mail.track.message.Chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by a.borodin on 02.12.2015.
 */
public class SqlChatDao extends AbstractJDBCDao<Chat> {
    private Connection connection;
    public SqlChatDao (Connection connection){
        super(connection);
    }

    private class PersistChat extends Chat {
        public void setId(Long id) {
            super.setId(id);
        }
    }

    @Override
    public String getSelectQuery() {
        return "SELECT id, title FROM chat ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO chat (title) \n" +
                "VALUES (?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE chat \n" +
                "SET title = ? \n" +
                "WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM chat WHERE id= ?;";
    }


    @Override
    public String getLastObject(){ return  "SELECT * FROM " + getSeqClass()+ " ORDER BY id DESC LIMIT 1;";}


    @Override
    protected ArrayList<Chat> parseResultSet(ResultSet rs) throws PersistException {
        ArrayList<Chat> result = new ArrayList<>();
        try {
            while (rs.next()) {
                PersistChat chat = new PersistChat();
                chat.setId(rs.getLong("id"));
                chat.setTitle(rs.getString("title"));
                result.add(chat);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Chat object) throws PersistException {
        try {
            statement.setString(1, object.getTitle());
            statement.setLong(2, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Chat object) throws PersistException {
        try {
            statement.setString(1, object.getTitle());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
    @Override
    protected String getSequence() {
        return "chat_id_seq";
    }

    @Override
    protected String getSeqClass() {
        return "chat";
    }
}
