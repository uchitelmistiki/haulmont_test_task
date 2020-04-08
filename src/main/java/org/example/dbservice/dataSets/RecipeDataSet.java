package org.example.dbservice.dataSets;

import java.sql.Date;
import static org.example.dbservice.DbProvider.dpv;

public class RecipeDataSet extends DataSet {
    private long recipeId;
    private String recipeDescription;
    private long recipePatientId;
    private long recipeDoctorId;
    private PatientDataSet patientInRecipe;
    private DoctorDataSet doctorInRecipe;
    private Date recipeDateOfCreation;
    private Date recipeValidity;
    private String recipePriority ;


    public RecipeDataSet(long recipeId, String recipeDescription, long recipePatientId, long recipeDoctorId,  Date recipeDateOfCreation, Date recipeValidity, String recipePriority) {

        this.patientInRecipe = dpv.getPatient(recipePatientId);
        this.doctorInRecipe = dpv.getDoctor(recipeDoctorId);

        this.recipeId = recipeId;
        this.recipeDescription = recipeDescription;
        this.recipePatientId = recipePatientId;
        this.recipeDoctorId = recipeDoctorId;
        this.recipeDateOfCreation = recipeDateOfCreation;
        this.recipeValidity = recipeValidity;
        this.recipePriority = recipePriority;
    }

    public RecipeDataSet(String recipeDescription, long recipePatientId, long recipeDoctorId,  Date recipeDateOfCreation, Date recipeValidity, String recipePriority) {

        this.patientInRecipe = dpv.getPatient(recipePatientId);
        this.doctorInRecipe = dpv.getDoctor(recipeDoctorId);

        this.recipeDescription = recipeDescription;
        this.recipePatientId = recipePatientId;
        this.recipeDoctorId = recipeDoctorId;
        this.recipeDateOfCreation = recipeDateOfCreation;
        this.recipeValidity = recipeValidity;
        this.recipePriority = recipePriority;
    }

    public String getPatientInRecipe() {
        return patientInRecipe.getFormattedPatientName();
    }

    public String getDoctorInRecipe() {
        return doctorInRecipe.getFormattedDoctorName();
    }

    public long getPatientInRecipeId(){
        return patientInRecipe.getPatientId();
    }

    public long getDoctorInRecipeId() {
        return doctorInRecipe.getDoctorId();
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }


    public long getRecipePatientId() {
        return recipePatientId;
    }

    public void setRecipePatientId(long recipePatientId) {
        this.recipePatientId = recipePatientId;
    }

    public long getRecipeDoctorId() {
        return recipeDoctorId;
    }

    public void setRecipeDoctorId(long recipeDoctorId) {
        this.recipeDoctorId = recipeDoctorId;
    }

    public Date getRecipeDateOfCreation() {
        return recipeDateOfCreation;
    }

    public void setRecipeDateOfCreation(Date recipeDateOfCreation) {
        this.recipeDateOfCreation = recipeDateOfCreation;
    }

    public Date getRecipeValidity() {
        return recipeValidity;
    }

    public void setRecipeValidity(Date recipeValidity) {
        this.recipeValidity = recipeValidity;
    }

    public String getRecipePriority() {
        return recipePriority;
    }

    public void setRecipePriority(String recipePriority) {
        this.recipePriority = recipePriority;
    }
}
