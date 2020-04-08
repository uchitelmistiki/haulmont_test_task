package org.example.dbservice.dao;

import org.example.dbservice.dataSets.DataSet;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
    List<? extends DataSet> getAll() throws SQLException;
    void insert(T value);
    void delete(long id) throws SQLException;
    void update(T value) throws SQLException;
    DataSet getById(long id) throws SQLException;
}
