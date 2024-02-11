package com.example.foodplanner.model.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.dto.PlanMeal;

import java.util.List;

@Dao
public interface PlanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(PlanMeal meal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllMeals(PlanMeal... meals);

    @Query("SELECT * FROM plan_meals")
    LiveData<List<PlanMeal>> getAllPlanMeals();

    @Query("SELECT * FROM plan_meals WHERE id= :id")
    LiveData<PlanMeal> getMealById(String id);

    @Query("SELECT * FROM plan_meals WHERE dayOfWeek= :day")
    LiveData<List<PlanMeal>> getMealByDay(String day);

    @Delete
    void deleteMeal(PlanMeal meal);
}
