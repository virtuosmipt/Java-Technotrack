package ru.mail.track.jdbc.DAO.DAOSql;

import ru.mail.track.jdbc.DAO.AbstractJDBCDao;
import ru.mail.track.jdbc.DAO.Exception.PersistException;
import ru.mail.track.message.ChatMessage;
import ru.mail.track.message.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by a.borodin on 02.12.2015.
 */
public class SqlMessageDao extends AbstractJDBCDao<ChatMessage> {
    private Connection connection;

    public SqlMessageDao(Connection connection) {
        super(connection);
    }

    private class PersistMessage extends ChatMessage {
        public void setId(Long id) {
            super.setMessageId(id);
        }
    }

    @Override
    public String getSelectQuery() {
        return "SELECT id, message, senderid, chatid, timestamp FROM public.message ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO public.message (message, senderid, chatid, timestamp) \n" +
                "VALUES (?, ?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE public.message \n" +
                "SET message = ?, senderid  = ?, chatid = ?, timestamp = ? \n" +
                "WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM public.message WHERE id= ?;";
    }

    @Override
    public String getLastObject(){ return  "SELECT * FROM " + getSeqClass()+ " ORDER BY id DESC LIMIT 1;";}


    @Override
    protected ArrayList<ChatMessage> parseResultSet(ResultSet rs) throws PersistException {
        ArrayList<ChatMessage> result = new ArrayList<>();
        try {
            while (rs.next()) {
                PersistMessage message = new PersistMessage();
                message.setId(rs.getLong("id"));
                message.setMessage(rs.getString("message"));
                message.setSenderId(rs.getLong("senderid"));
                message.setChatId(rs.getLong("chatid"));
                message.setTimestamp(rs.getString("timestamp"));
                result.add(message);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, ChatMessage object) throws PersistException {
        try {
            statement.setString(1, object.getMessage());
            statement.setLong(2, object.getSenderId());
            statement.setLong(3, object.getChatId());
            statement.setString(4, object.getTimestamp());
            statement.setLong(5, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, ChatMessage object) throws PersistException {
        try {
            statement.setString(1, object.getMessage());
            statement.setLong(2, object.getSenderId());
            statement.setLong(3, object.getChatId());
            statement.setString(4, object.getTimestamp());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected String getSequence() {
        return "message_id_seq";
    }

    @Override
    protected String getSeqClass() {
        return "message";
    }
}
