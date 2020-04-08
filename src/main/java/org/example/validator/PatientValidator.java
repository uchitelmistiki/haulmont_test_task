package org.example.validator;

public class PatientValidator extends Validator{

    private String patientName;
    private String patientLastName;
    private String patientSecondName;
    private String patientsPhone;

    public PatientValidator(String patientName, String patientLastName, String patientSecondName, String patientsPhone) {
        this.patientName = patientName;
        this.patientLastName = patientLastName;
        this.patientSecondName = patientSecondName;
        this.patientsPhone = patientsPhone;
    }

    public String getPatientName() {
        return transformToOnlyText(patientName);
    }

    public String getPatientLastName() {
        return transformToOnlyText(patientLastName);
    }

    public String getPatientSecondName() {
        return transformToOnlyText(patientSecondName);
    }
    public String getPatientsPhone() {
        return patientsPhone;
    }

    public String validatePatientToEmptyValues(){
        String status = "Ok";
        String message = "";

        if(!isEmptyValue("Имя", this.patientName ).equals("Ok")){
            message+=isEmptyValue("Имя", this.patientName )+"\n";
        }
        if(!isEmptyValue("Фамилия", this.patientLastName ).equals("Ok")){
            message+=isEmptyValue("Фамилия", this.patientLastName )+"\n";
        }
        if(!isEmptyValue("Отчество", this.patientSecondName ).equals("Ok")){
            message+=isEmptyValue("Отчество", this.patientSecondName )+"\n";
        }
        if(!isEmptyValue("Телефон", this.patientsPhone ).equals("Ok")){
            message+=isEmptyValue("Телефон", this.patientsPhone )+"\n";
        }

        if(message.equals("")) {
            return status;
        }
        return message;
    }

    public String validatePatientPhoneNumber(){
        String status = "Ok";
        String message = "";

        if(!isValidNumber(this.patientsPhone)){
            message+="Некорректное значение номера телефона!"+"\n";
        }
        if (this.patientsPhone.length() > 10) {
            message += "Длинна номера телефона должна быть не более 10 цифр!" + "\n";
        }
        if(message.equals("")) {
            return status;
        }
        return message;
    }

    public String validatePatientFields(){
        String status = "Ok";
        String message = "";

        String emptyFieldsValidateStatus = "";
        String phoneNumberValidateStatus = "";
        emptyFieldsValidateStatus = validatePatientToEmptyValues();
        phoneNumberValidateStatus = validatePatientPhoneNumber();

        if(!emptyFieldsValidateStatus.equals("Ok")){
            message+=emptyFieldsValidateStatus;
        }

        if(!phoneNumberValidateStatus.equals("Ok")){
            message+=phoneNumberValidateStatus;
        }

        if(message.equals("")) {
            return status;
        }
        return message;
    }
}
