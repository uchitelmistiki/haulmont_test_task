package org.example.dbservice.dataSets;

import java.sql.Date;

public class DoctorStatDataSet {

    private Date groupDateCreateRecipe;
    private int countRecipe;

    public DoctorStatDataSet(int countRecipe, Date groupDateCreateRecipe) {
        this.groupDateCreateRecipe = groupDateCreateRecipe;
        this.countRecipe = countRecipe;
    }

    public Date getGroupDateCreateRecipe() {
        return groupDateCreateRecipe;
    }

    public int getCountRecipe() {
        return countRecipe;
    }
}
