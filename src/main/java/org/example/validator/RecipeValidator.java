package org.example.validator;

import java.time.LocalDate;

public class RecipeValidator extends Validator {
    private String recipeDescription;
    private String patient;
    private String doctor;
    private LocalDate recipeDateOfCreation;
    private LocalDate recipeValidity;
    private String recipePriority ;
    private long recipePatientId;
    private long recipeDoctorId;


    public RecipeValidator(String recipeDescription, String patient, String doctor, LocalDate recipeDateOfCreation, LocalDate recipeValidity, String recipePriority) {
        this.recipeDescription = recipeDescription;
        this.patient = patient;
        this.doctor = doctor;
        this.recipeDateOfCreation = recipeDateOfCreation;
        this.recipeValidity = recipeValidity;
        this.recipePriority = recipePriority;
    }

    public long getRecipePatientId() {
        return recipePatientId;
    }

    public long getRecipeDoctorId() {
        return recipeDoctorId;
    }

    public String getRecipePriority() {
        return recipePriority;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public LocalDate getRecipeDateOfCreation() {
        return recipeDateOfCreation;
    }

    public LocalDate getRecipeValidity() {
        return recipeValidity;
    }

    public void setRecipePatientId(long recipePatientId) {
        this.recipePatientId = recipePatientId;
    }

    public void setRecipeDoctorId(long recipeDoctorId) {
        this.recipeDoctorId = recipeDoctorId;
    }

    public String validateRecipeToEmptyValues(){
        String status = "Ok";
        String message = "";

        if(!isEmptyValue("Описание", this.recipeDescription ).equals("Ok")){
            message+=isEmptyValue("Описание", this.recipeDescription )+"\n";
        }
        if(!isEmptyValue("Пациент", this.patient ).equals("Ok")){
            message+=isEmptyValue("Пациент", this.patient )+"\n";
        }
        if(!isEmptyValue("Врач", this.doctor ).equals("Ok")){
            message+=isEmptyValue("Врач", this.doctor )+"\n";
        }
        if(!isNullDateValue("Дата создания", this.recipeDateOfCreation).equals("Ok")){
            message+=isNullDateValue("Дата создания", this.recipeDateOfCreation)+"\n";
        }
        if(!isNullDateValue("Срок действия", this.recipeValidity).equals("Ok")){
            message+=isNullDateValue("Срок действия", this.recipeValidity)+"\n";
        }

        if(message.equals("")) {
            return status;
        }

        return message;
    }

    public String validateRecipeFields(){
        String status = "Ok";
        String message = "";

        String emptyFieldsValidateStatus = "";
        emptyFieldsValidateStatus = validateRecipeToEmptyValues();

        if(!emptyFieldsValidateStatus.equals("Ok")){
            message=emptyFieldsValidateStatus;
        }

        if(message.equals("")) {
            return status;
        }
        return message;
    }
}
