package org.example.dbservice.dao;

import org.example.dbservice.executor.DBExecutor;
import org.example.dbservice.dataSets.DoctorDataSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO implements DAO {

    private DoctorDataSet doctor;
    private DBExecutor dbex;

    public DoctorDAO(Connection connection) {
        dbex = new DBExecutor(connection);
    }

    @Override
    public List<DoctorDataSet> getAll() {
        try {
            return dbex.execQuery("SELECT * FROM doctors", result -> {
                List<DoctorDataSet> doctors = new ArrayList<DoctorDataSet>();
                while (result.next()) {
                    doctors.add(new DoctorDataSet(
                            result.getLong(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            result.getString(5)
                    ));
                }
                return doctors;
            });
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(Object value) {
        try {
            this.doctor = (DoctorDataSet) value;
            String sql = "INSERT INTO doctors VALUES(NULL, ?, ?, ?, ?)";
            dbex.execPreparedUpdate(sql,preparedStatement->{
                preparedStatement.setString(1, doctor.getDoctorName());
                preparedStatement.setString(2, doctor.getDoctorLastName());
                preparedStatement.setString(3, doctor.getDoctorSecondName());
                preparedStatement.setString(4, doctor.getDoctorSpecialization());
                return preparedStatement;
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try {
            dbex.execUpdate("DELETE FROM doctors WHERE doctorId = '"+id+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Object value) {
        try {
            this.doctor = (DoctorDataSet) value;
            String sql = "UPDATE doctors SET doctorName = ?, doctorLastName = ?, doctorSecondName = ?, doctorSpecialization = ? WHERE doctorId = ?";
            dbex.execPreparedUpdate(sql,preparedStatement->{
                preparedStatement.setString(1, doctor.getDoctorName());
                preparedStatement.setString(2, doctor.getDoctorLastName());
                preparedStatement.setString(3, doctor.getDoctorSecondName() );
                preparedStatement.setString(4, doctor.getDoctorSpecialization());
                preparedStatement.setLong(5, doctor.getDoctorId());
                return preparedStatement;
            });
        } catch (SQLException se){
            se.printStackTrace();
        }
    }

    @Override
    public DoctorDataSet getById(long id) {
        try {
            return dbex.execQuery("SELECT * FROM doctors WHERE doctorId = '" + id + "'", result -> {
                result.next();
                DoctorDataSet doctor = new DoctorDataSet(
                        result.getLong(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5)
                );
                return doctor;
            });
        }catch (SQLException se){
            se.printStackTrace();
        }
        return null;
    }

    public int getCountItemDoctorInRecipe(long id){
        try {
            return dbex.execQuery("SELECT COUNT(*) FROM recipes WHERE recipeDoctorId = '"+id+"'", result -> {
                int resultCount;
                result.next();
                resultCount = result.getInt(1);
                return resultCount;
            });
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
