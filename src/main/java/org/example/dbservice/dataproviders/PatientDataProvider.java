package org.example.dbservice.dataproviders;

import org.example.dbservice.dataSets.PatientDataSet;
import org.example.validator.PatientValidator;
import java.util.List;
import static org.example.dbservice.DbProvider.dpv;

public class PatientDataProvider {

    public PatientDataSet getPatientByIdFromDB(long id){
        return dpv.getPatient(id);
    }

    public List<PatientDataSet> getAllPatientsFromDB(){
        return dpv.getAllPatients();
    }

    public void addPatientToDB(PatientValidator pv){
        dpv.addNewPatient(
                pv.getPatientName(),
                pv.getPatientLastName(),
                pv.getPatientSecondName(),
                pv.getPatientsPhone()
        );
    }

    public void updatePatientInDB(PatientDataSet pd, PatientValidator pv)  {
        pd.setPatientName(pv.getPatientName());
        pd.setPatientLastName(pv.getPatientLastName());
        pd.setPatientSecondName(pv.getPatientSecondName());
        pd.setPatientsPhone(pv.getPatientsPhone());
        dpv.updatePatient(pd);
    }

    public boolean deletePatientFromDB(PatientDataSet pd){
        if(dpv.getPatientCountFromRecipe(pd.getPatientId())==0){
            dpv.deletePatientById(pd.getPatientId());
            return true;
        }else{
            return false;
        }
    }

}
