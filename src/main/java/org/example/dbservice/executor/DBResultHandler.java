package org.example.dbservice.executor;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DBResultHandler<T> {
    T handle (ResultSet resultSet) throws SQLException;
}
