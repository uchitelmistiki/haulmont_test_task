package org.example.dbservice.dataproviders;

import org.example.dbservice.dataSets.DoctorDataSet;
import org.example.dbservice.dataSets.DoctorStatDataSet;
import org.example.validator.DoctorValidator;
import java.util.List;
import static org.example.dbservice.DbProvider.dpv;

public class DoctorDataProvider {

    public DoctorDataSet getDoctorByIdFromDB(long id){
        return dpv.getDoctor(id);
    }

    public List<DoctorDataSet> getAllDoctorsFromDB(){
        return dpv.getAllDoctors();
    }

    public void addDoctorToDB(DoctorValidator dv){
        dpv.addNewDoctor(
                dv.getDoctorName(),
                dv.getDoctorLastName(),
                dv.getDoctorSecondName(),
                dv.getDoctorSpecialization()
        );
    }

    public void updateDoctorInDB(DoctorDataSet dd, DoctorValidator dv)  {
        dd.setDoctorName(dv.getDoctorName());
        dd.setDoctorLastName(dv.getDoctorLastName());
        dd.setDoctorSecondName(dv.getDoctorSecondName());
        dd.setDoctorSpecialization(dv.getDoctorSpecialization());
        dpv.updateDoctor(dd);
    }

    public boolean deleteDoctorFromDB(DoctorDataSet dd){
        if(dpv.getDoctorCountFromRecipe(dd.getDoctorId())==0) {
            dpv.deleteDoctorById(dd.getDoctorId());
            return true;
        }else{
            return false;
        }
    }

    public List<DoctorStatDataSet> getDoctorStatByIdFromDB(long id){
        return dpv.getDoctorStat(id);
    }

    public int getCountOfDoctorStatByIdFromDB(long id){
        return dpv.getCountOfDoctorStat(id);
    }
}
