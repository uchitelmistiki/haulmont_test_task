package org.example.dbservice.dataSets;

public class PatientDataSet extends DataSet{
    private long patientId;
    private String patientName;
    private String patientLastName;
    private String patientSecondName;
    private String patientsPhone;


    public PatientDataSet(long patientId, String patientName, String patientLastName, String patientSecondName, String patientsPhone) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.patientLastName = patientLastName;
        this.patientSecondName = patientSecondName;
        this.patientsPhone = patientsPhone;
    }

    public PatientDataSet(String patientName, String patientLastName, String patientSecondName, String patientsPhone) {
        this.patientName = patientName;
        this.patientLastName = patientLastName;
        this.patientSecondName = patientSecondName;
        this.patientsPhone = patientsPhone;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public String getPatientSecondName() {
        return patientSecondName;
    }

    public void setPatientSecondName(String patientSecondName) {
        this.patientSecondName = patientSecondName;
    }

    public String getPatientsPhone() {
        return patientsPhone;
    }

    public void setPatientsPhone(String patientsPhone) {
        this.patientsPhone = patientsPhone;
    }

    public String getFormattedPatientName(){
        StringBuilder strb = new StringBuilder();
        String delimiter = " ";
        return strb.append(patientLastName).append(delimiter)
                .append(patientName).append(delimiter)
                .append(patientSecondName).toString();
    }
}