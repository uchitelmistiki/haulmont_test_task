package org.example.dbservice.executor;

import java.sql.*;

public class DBExecutor {
    private final Connection connection;

    public DBExecutor(Connection connection) {
        this.connection = connection;
    }

    public void execUpdate(String update) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(update);
        stmt.close();
    }

    public <T> T execQuery(String query, DBResultHandler<T> handler) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(query);
        ResultSet result = stmt.getResultSet();
        T value = handler.handle(result);
        result.close();
        stmt.close();
        return value;
    }

    public void execPreparedUpdate(String update, DBRowMapper dbm) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement(update);
        preparedStatement = (PreparedStatement) dbm.mapp(preparedStatement);
        preparedStatement.execute();
        preparedStatement.close();
    }

}
