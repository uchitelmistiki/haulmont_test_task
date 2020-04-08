package org.example.dbservice.dao;

import org.example.dbservice.dataSets.DoctorStatDataSet;
import org.example.dbservice.dataSets.RecipeDataSet;
import org.example.dbservice.executor.DBExecutor;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeDAO implements DAO {

    private RecipeDataSet recipe;
    private DBExecutor dbex;

    public RecipeDAO(Connection connection) {
        dbex = new DBExecutor(connection);
    }

    @Override
    public List<RecipeDataSet> getAll() {
        try {
            return dbex.execQuery("SELECT * FROM recipes", result -> {
                List<RecipeDataSet> recipes = new ArrayList<RecipeDataSet>();
                while (result.next()) {
                    recipes.add(new RecipeDataSet(
                            result.getLong(1),
                            result.getString(2),
                            result.getLong(3),
                            result.getLong(4),
                            result.getDate(5),
                            result.getDate(6),
                            result.getString(7)
                    ));
                }
                return recipes;
            });
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(Object value) {
        try {
            this.recipe = (RecipeDataSet) value;
            String sql = "INSERT INTO recipes VALUES(NULL, ?, ?, ?, ?, ?, ?)";
            dbex.execPreparedUpdate(sql,preparedStatement->{
                preparedStatement.setString(1, recipe.getRecipeDescription());
                preparedStatement.setLong(2, recipe.getPatientInRecipeId());
                preparedStatement.setLong(3, recipe.getDoctorInRecipeId());
                preparedStatement.setDate(4, recipe.getRecipeDateOfCreation());
                preparedStatement.setDate(5, recipe.getRecipeValidity());
                preparedStatement.setString(6, recipe.getRecipePriority());
                return preparedStatement;
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try {
            dbex.execUpdate("DELETE FROM recipes WHERE recipeId = '"+id+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Object value){
        try {
            this.recipe = (RecipeDataSet) value;
            String sql = "UPDATE recipes SET recipeDescription = ?, recipePatientId = ?, recipeDoctorId  = ?, recipeDateOfCreation = ?, recipeValidity = ?, recipePriority = ?  WHERE recipeId = ?";
            dbex.execPreparedUpdate(sql, preparedStatement -> {
                preparedStatement.setString(1, recipe.getRecipeDescription());
                preparedStatement.setLong(2, recipe.getRecipePatientId());
                preparedStatement.setLong(3, recipe.getRecipeDoctorId());
                preparedStatement.setDate(4, recipe.getRecipeDateOfCreation());
                preparedStatement.setDate(5, recipe.getRecipeValidity());
                preparedStatement.setString(6, recipe.getRecipePriority());
                preparedStatement.setLong(7, recipe.getRecipeId());
                return preparedStatement;
            });
        }catch (SQLException se){
            se.printStackTrace();
        }
    }

    @Override
    public RecipeDataSet getById(long id) {
        return null;
    }

    public List<DoctorStatDataSet> getRecipeStatisticsByDoctor(long doctorId) {
        try {
            return dbex.execQuery("SELECT COUNT(*), recipeDateOfCreation FROM recipes WHERE recipeDoctorId = '"+doctorId+"' GROUP BY recipeDateOfCreation", result -> {
                List<DoctorStatDataSet> recipesStat = new ArrayList<>();
                while (result.next()) {
                    recipesStat.add(new DoctorStatDataSet(
                            result.getInt(1),
                            result.getDate(2)

                    ));
                }
                return recipesStat;
            });
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCountOfRecipeStatisticsByDoctor(long doctorId){
        try {
            return dbex.execQuery("SELECT COUNT(*) FROM recipes WHERE recipeDoctorId = '"+doctorId+"'", result -> {
                int resultCount;
                result.next();
                resultCount = result.getInt(1);
                return resultCount;
            });
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
