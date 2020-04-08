package org.example.dbservice.dataproviders;

import org.example.dbservice.dataSets.RecipeDataSet;
import org.example.validator.RecipeValidator;
import java.sql.Date;
import java.util.List;
import static org.example.dbservice.DbProvider.dpv;

public class RecipeDataProvider {

    public List<RecipeDataSet> getAllRecipesFromDB(){
        return dpv.getAllRecipes();
    }

    public void addRecipeToDB(RecipeValidator rv){
        dpv.addNewRecipe(rv.getRecipeDescription(),
                rv.getRecipePatientId(),
                rv.getRecipeDoctorId(),
                Date.valueOf(rv.getRecipeDateOfCreation()),
                Date.valueOf(rv.getRecipeValidity()),
                rv.getRecipePriority()
        );
    }

    public void updateRecipeInDB(RecipeDataSet rd, RecipeValidator rv)  {
        rd.setRecipeDescription(rv.getRecipeDescription());
        rd.setRecipePatientId(rv.getRecipePatientId());
        rd.setRecipeDoctorId(rv.getRecipeDoctorId());
        rd.setRecipeDateOfCreation(Date.valueOf(rv.getRecipeDateOfCreation()));
        rd.setRecipeValidity(Date.valueOf(rv.getRecipeValidity()));
        rd.setRecipePriority(rv.getRecipePriority());
        dpv.updateRecipe(rd);
    }

    public void deleteRecipeFromDB(RecipeDataSet rd){
            dpv.deleteRecipeById(rd.getRecipeId());
    }
}
