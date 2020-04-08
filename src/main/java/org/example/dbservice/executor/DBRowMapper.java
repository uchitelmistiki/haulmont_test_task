package org.example.dbservice.executor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface DBRowMapper<T> {
    T mapp (PreparedStatement preparedStatement) throws SQLException;
}
