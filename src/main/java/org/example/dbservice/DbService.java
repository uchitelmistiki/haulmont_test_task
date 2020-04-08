package org.example.dbservice;

import org.example.dbservice.dao.DoctorDAO;
import org.example.dbservice.dao.PatientDAO;
import org.example.dbservice.dao.RecipeDAO;
import org.example.dbservice.dataSets.DoctorDataSet;
import org.example.dbservice.dataSets.DoctorStatDataSet;
import org.example.dbservice.dataSets.PatientDataSet;
import org.example.dbservice.dataSets.RecipeDataSet;
import org.example.dbservice.executor.DBExecutor;
import java.sql.*;
import java.util.List;

public class DbService {

    private final Connection connection;
    private DBExecutor dbex;
    private PatientDAO patientDAO;
    private DoctorDAO doctorDAO;
    private RecipeDAO recipeDAO;

    public DbService(){
        this.connection = getConnect();
        dbex = new DBExecutor(this.connection);
        patientDAO = new PatientDAO(this.connection);
        doctorDAO = new DoctorDAO(this.connection);
        recipeDAO = new RecipeDAO(this.connection);
    }

    private Connection getConnect() {

        String path = "./";
        String dbname = "testdb";
        String connectionString = "jdbc:hsqldb:file:"+path+dbname;
        String user = "SA";
        String password = "";

        try{
            Connection connection = DriverManager.getConnection(connectionString, user, password);
            return connection;
        }catch (SQLException e) {
            System.out.println("Соединение не создано");
            e.printStackTrace();
        }
        return null;
    }

    public void createPatientsTable(){
        String sql ="CREATE TABLE IF NOT EXISTS patients (" +
                    "patientId BIGINT IDENTITY PRIMARY KEY," +
                    "patientName VARCHAR(255)," +
                    "patientLastName VARCHAR(255)," +
                    "patientSecondName VARCHAR(255)," +
                    "patientsPhone VARCHAR(10)" +
                    ")";
        try {
            dbex.execUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createDoctorsTable(){
        String sql = "CREATE TABLE IF NOT EXISTS doctors (" +
                     "doctorId BIGINT IDENTITY PRIMARY KEY," +
                     "doctorName VARCHAR(255)," +
                     "doctorLastName VARCHAR(255)," +
                     "doctorSecondName VARCHAR(255)," +
                     "doctorSpecialization VARCHAR(255)" +
                     ")";
        try {
            dbex.execUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createRecipesTable(){
        String sql = "CREATE TABLE IF NOT EXISTS recipes (" +
                     "recipeId BIGINT IDENTITY PRIMARY KEY," +
                     "recipeDescription LONGVARCHAR," +
                     "recipePatientId BIGINT," +
                     "recipeDoctorId BIGINT," +
                     "recipeDateOfCreation DATE," +
                     "recipeValidity DATE," +
                     "recipePriority VARCHAR(255)," +
                     "FOREIGN KEY (recipePatientId) REFERENCES patients(patientId) ON DELETE RESTRICT," +
                     "FOREIGN KEY (recipeDoctorId) REFERENCES doctors(doctorId) ON DELETE RESTRICT"+
                     ")";
        try {
            dbex.execUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //*********************Пациенты***********************

    public void insertPatient(String name, String lastName, String secondName, String phone) {
        patientDAO.insert(new PatientDataSet(name, lastName, secondName, phone));
    }

    public List<PatientDataSet> getListOfPatients() {
        return patientDAO.getAll();
    }

    public PatientDataSet getPatientById(long id){
        return patientDAO.getById(id);
    }

    public void deletePatient(long id) {
        patientDAO.delete(id);
    }

    public void updatePatient(PatientDataSet patient) {
        patientDAO.update(patient);
    }

    public int getCountItemPatientFromRecipes(long id){
        return patientDAO.getCountItemPatientInRecipe(id);
    }

    //*********************Врачи***********************

    public void insertDoctor(String name, String lastName, String secondName, String specialization){
        doctorDAO.insert(new DoctorDataSet(name, lastName, secondName, specialization));
    }

    public List<DoctorDataSet> getListOfDoctors(){
        return doctorDAO.getAll();
    }

    public DoctorDataSet getDoctorById(long id){
        return doctorDAO.getById(id);
    }

    public void deleteDoctor(long id){
        doctorDAO.delete(id);
    }

    public void updateDoctor(DoctorDataSet doctor){
        doctorDAO.update(doctor);
    }

    public List<DoctorStatDataSet> getDoctorStat(long id){
        return recipeDAO.getRecipeStatisticsByDoctor(id);
    }

    public int getCountOfDoctorStat(long id){
        return recipeDAO.getCountOfRecipeStatisticsByDoctor(id);
    }

    public int getCountItemDoctorFromRecipes(long id){
        return doctorDAO.getCountItemDoctorInRecipe(id);
    }

    //*********************Рецепты***********************

    public List<RecipeDataSet> getListOfRecipes() {
        return recipeDAO.getAll();
    }

    public void insertRecipe(String recipeDescription, long recipePatientId, long recipeDoctorId, Date recipeDateOfCreation, Date recipeValidity, String recipePriority){
        recipeDAO.insert(new RecipeDataSet(recipeDescription, recipePatientId, recipeDoctorId, recipeDateOfCreation, recipeValidity, recipePriority));
    }

    public void deleteRecipe(long id){
        recipeDAO.delete(id);
    }

    public void updateRecipe(RecipeDataSet recipe){
        recipeDAO.update(recipe);
    }
}
