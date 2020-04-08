package org.example.dbservice.dataSets;

public class DoctorDataSet extends DataSet {

    private long doctorId;
    private String doctorName;
    private String doctorLastName;
    private String doctorSecondName;
    private String doctorSpecialization;

    public DoctorDataSet(long doctorId, String doctorName, String doctorLastName, String doctorSecondName, String doctorSpecialization) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.doctorLastName = doctorLastName;
        this.doctorSecondName = doctorSecondName;
        this.doctorSpecialization = doctorSpecialization;
    }

    public DoctorDataSet(String doctorName, String doctorLastName, String doctorSecondName, String doctorSpecialization) {
        this.doctorName = doctorName;
        this.doctorLastName = doctorLastName;
        this.doctorSecondName = doctorSecondName;
        this.doctorSpecialization = doctorSpecialization;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorLastName() {
        return doctorLastName;
    }

    public void setDoctorLastName(String doctorLastName) {
        this.doctorLastName = doctorLastName;
    }

    public String getDoctorSecondName() {
        return doctorSecondName;
    }

    public void setDoctorSecondName(String doctorSecondName) {
        this.doctorSecondName = doctorSecondName;
    }

    public String getDoctorSpecialization() {
        return doctorSpecialization;
    }

    public void setDoctorSpecialization(String doctorSpecialization) {
        this.doctorSpecialization = doctorSpecialization;
    }

    public String getFormattedDoctorName(){
        StringBuilder strb = new StringBuilder();
        String delimiter = " ";
        return strb.append(doctorLastName).append(delimiter)
                .append(doctorName).append(delimiter)
                .append(doctorSecondName).toString();
    }

}
