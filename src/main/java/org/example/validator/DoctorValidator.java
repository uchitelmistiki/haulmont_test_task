package org.example.validator;

public class DoctorValidator extends Validator{
    private String doctorName;
    private String doctorLastName;
    private String doctorSecondName;
    private String doctorSpecialization;

    public DoctorValidator(String doctorName, String doctorLastName, String doctorSecondName, String doctorSpecialization) {
        this.doctorName = doctorName;
        this.doctorLastName = doctorLastName;
        this.doctorSecondName = doctorSecondName;
        this.doctorSpecialization = doctorSpecialization;
    }

    public String getDoctorName() {
        return transformToOnlyText(doctorName);
    }

    public String getDoctorLastName() {
        return transformToOnlyText(doctorLastName);
    }

    public String getDoctorSecondName() {
        return transformToOnlyText(doctorSecondName);
    }

    public String getDoctorSpecialization() {
        return transformToOnlyText(doctorSpecialization);
    }

    public String validateDoctorToEmptyValues(){
        String status = "Ok";
        String message = "";

        if(!isEmptyValue("Имя", this.doctorName ).equals("Ok")){
            message+=isEmptyValue("Имя", this.doctorName )+"\n";
        }
        if(!isEmptyValue("Фамилия", this.doctorLastName ).equals("Ok")){
            message+=isEmptyValue("Фамилия", this.doctorLastName )+"\n";
        }
        if(!isEmptyValue("Отчество", this.doctorSecondName ).equals("Ok")){
            message+=isEmptyValue("Отчество", this.doctorSecondName )+"\n";
        }
        if(!isEmptyValue("Специализация", this.doctorSpecialization ).equals("Ok")){
            message+=isEmptyValue("Специализация", this.doctorSpecialization )+"\n";
        }

        if(message.equals("")) {
            return status;
        }

        return message;
    }

    public String validateDoctorFields(){
        String status = "Ok";
        String message = "";

        String emptyFieldsValidateStatus = "";
        emptyFieldsValidateStatus = validateDoctorToEmptyValues();

        if(!emptyFieldsValidateStatus.equals("Ok")){
            message=emptyFieldsValidateStatus;
        }

        if(message.equals("")) {
            return status;
        }
        return message;
    }
}
