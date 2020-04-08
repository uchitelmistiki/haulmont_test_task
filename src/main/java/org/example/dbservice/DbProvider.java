package org.example.dbservice;

import org.example.dbservice.dataSets.DoctorDataSet;
import org.example.dbservice.dataSets.DoctorStatDataSet;
import org.example.dbservice.dataSets.PatientDataSet;
import org.example.dbservice.dataSets.RecipeDataSet;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class DbProvider {

    private DbService dbs;
    public static DbProvider dpv;

    private DbProvider(){
        try {
            createDbService();
            preparedTables();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void init() {
        try {
            dpv = new DbProvider();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createDbService(){
        this.dbs = new DbService();
    }

    public void preparedTables() {
        try {
            this.dbs.createPatientsTable();
            this.dbs.createDoctorsTable();
            this.dbs.createRecipesTable();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //*********************Пациенты***********************

    public List<PatientDataSet> getAllPatients(){
        return dbs.getListOfPatients();
    }

    public PatientDataSet getPatient(long id){
        return dbs.getPatientById(id);
    }

    public void addNewPatient(String name, String lastName, String secondName, String phone){
        dbs.insertPatient(name, lastName, secondName, phone);
    }

    public void deletePatientById(long id){
        dbs.deletePatient(id);
    }

    public void updatePatient(PatientDataSet patient){
        dbs.updatePatient(patient);
    }

    public int  getPatientCountFromRecipe(long id){
        return dbs.getCountItemPatientFromRecipes(id);
    }


    //*********************Врачи***********************

    public List<DoctorDataSet> getAllDoctors(){
        return dbs.getListOfDoctors();
    }

    public DoctorDataSet getDoctor(long id){
        return dbs.getDoctorById(id);
    }

    public void addNewDoctor(String name, String lastName, String secondName, String specialization){
        dbs.insertDoctor(name, lastName, secondName, specialization);
    }

    public void deleteDoctorById(long id){
        dbs.deleteDoctor(id);
    }

    public void updateDoctor(DoctorDataSet doctor){
        dbs.updateDoctor(doctor);
    }

    public List<DoctorStatDataSet> getDoctorStat(long id){
        return dbs.getDoctorStat(id);
    }

    public int getCountOfDoctorStat(long id){
        return dbs.getCountOfDoctorStat(id);
    }

    public int getDoctorCountFromRecipe(long id){
        return dbs.getCountItemDoctorFromRecipes(id);
    }


    //*********************Рецепты***********************

    public List<RecipeDataSet> getAllRecipes(){
        return dbs.getListOfRecipes();
    }

    public void addNewRecipe(String recipeDescription, long recipePatientId, long recipeDoctorId, Date recipeDateOfCreation, Date recipeValidity, String recipePriority){
       dbs.insertRecipe(recipeDescription, recipePatientId, recipeDoctorId, recipeDateOfCreation, recipeValidity, recipePriority);
    }

    public void deleteRecipeById(long id){
        dbs.deleteRecipe(id);
    }

    public void updateRecipe(RecipeDataSet recipe){
        dbs.updateRecipe(recipe);
    }
}
