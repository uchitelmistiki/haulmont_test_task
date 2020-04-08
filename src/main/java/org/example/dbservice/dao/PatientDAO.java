package org.example.dbservice.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.example.dbservice.executor.DBExecutor;
import org.example.dbservice.dataSets.PatientDataSet;

public class PatientDAO implements DAO{

    private PatientDataSet patient;
    private DBExecutor dbex;

    public PatientDAO(Connection connection) {
        dbex = new DBExecutor(connection);
    }

    @Override
    public List<PatientDataSet> getAll(){
        try {
           return dbex.execQuery("SELECT * FROM patients", result -> {
                List<PatientDataSet> patients = new ArrayList<PatientDataSet>();
                while (result.next()) {
                    patients.add(new PatientDataSet(
                            result.getLong(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            result.getString(5)
                    ));
                }
                return patients;
            });
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(Object value) {
        try {
            this.patient = (PatientDataSet) value;
            String sql = "INSERT INTO patients VALUES(NULL, ?, ?, ?, ?)";
            dbex.execPreparedUpdate(sql,preparedStatement->{
                preparedStatement.setString(1, patient.getPatientName());
                preparedStatement.setString(2, patient.getPatientLastName());
                preparedStatement.setString(3, patient.getPatientSecondName() );
                preparedStatement.setString(4, patient.getPatientsPhone());
                return preparedStatement;
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id)  {
        try {
            dbex.execUpdate("DELETE FROM patients WHERE patientId = '"+id+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Object value){
       try {
           this.patient = (PatientDataSet) value;
           String sql = "UPDATE patients SET patientName = ?, patientLastName = ?, patientSecondName = ?, patientsPhone = ? WHERE patientId = ?";
           dbex.execPreparedUpdate(sql,preparedStatement->{
               preparedStatement.setString(1, patient.getPatientName());
               preparedStatement.setString(2, patient.getPatientLastName());
               preparedStatement.setString(3, patient.getPatientSecondName() );
               preparedStatement.setString(4, patient.getPatientsPhone());
               preparedStatement.setLong(5, patient.getPatientId());
               return preparedStatement;
           });
        } catch (SQLException se){
            se.printStackTrace();
        }
    }

    @Override
    public PatientDataSet getById(long id) {
        try {
            return dbex.execQuery("SELECT * FROM patients WHERE patientId = '" + id + "'", result -> {
                result.next();
                PatientDataSet patient = new PatientDataSet(
                        result.getLong(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5)
                );
                return patient;
            });
        }catch (SQLException se){
            se.printStackTrace();
        }
        return null;
    }

    public int getCountItemPatientInRecipe(long id){
        try {
            return dbex.execQuery("SELECT COUNT(*) FROM recipes WHERE recipePatientId = '"+id+"'", result -> {
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
