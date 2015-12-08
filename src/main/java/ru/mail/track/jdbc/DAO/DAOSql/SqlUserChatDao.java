package ru.mail.track.jdbc.DAO.DAOSql;

import ru.mail.track.jdbc.DAO.AbstractJDBCDao;
import ru.mail.track.jdbc.DAO.Exception.PersistException;
import ru.mail.track.message.UserChatRelation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by a.borodin on 02.12.2015.
 */
public class SqlUserChatDao extends AbstractJDBCDao<UserChatRelation> {
    private class PersistUserChatRelation extends UserChatRelation {
        public void setId(Long id) {
            super.setId(id);
        }
    }
    @Override
    public String getSelectQuery() {
        return "SELECT id, idchat, iduser FROM chat_user ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO chat_user (idchat, iduser) \n" +
                "VALUES (?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE chat_user \n" +
                "SET idchat = ?, iduser  = ? \n" +
                "WHERE id = ?;";
    }
    @Override
    public String getLastObject(){ return  "SELECT * FROM " + getSeqClass()+ " ORDER BY id DESC LIMIT 1;";}
    @Override
    public String getDeleteQuery() {
        return "DELETE FROM chat_user WHERE id= ?;";
    }
    @Override
    protected ArrayList<UserChatRelation> parseResultSet(ResultSet rs) throws PersistException {
        ArrayList<UserChatRelation> result = new ArrayList<>();
        try {
            while (rs.next()) {
                PersistUserChatRelation userChatRelation = new PersistUserChatRelation();
                userChatRelation.setId(rs.getLong("id"));
                userChatRelation.setChatId(rs.getLong("idchat"));
                userChatRelation.setUserId(rs.getLong("iduser"));
                result.add(userChatRelation);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, UserChatRelation object) throws PersistException {
        try {
            statement.setLong(1, object.getChatId());
            statement.setLong(2, object.getUserId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, UserChatRelation object) throws PersistException {
        try {
            statement.setLong(1, object.getChatId());
            statement.setLong(2, object.getUserId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    public SqlUserChatDao(Connection connection) {
        super(connection);
    }
    @Override
    protected String getSequence() {
        return "chatuser_id_seq";
    }

    @Override
    protected String getSeqClass() {
        return "chat_user";
    }
}
