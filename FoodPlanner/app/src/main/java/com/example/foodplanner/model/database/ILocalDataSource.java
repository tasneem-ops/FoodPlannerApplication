package com.example.foodplanner.model.database;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.dto.PlanMeal;

import java.util.List;

public interface ILocalDataSource {
    void insertMealToFav(Meal meal);
    void insertAllMealsToFav(Meal... meal);
    LiveData<List<Meal>> getAllFavorite();
    LiveData<Meal> getFavMealById(String id);
    void deleteMealFromFav(Meal meal);

    void insertMealToPlan(PlanMeal meal);
    void insertAllMealsToPlan(PlanMeal... meals);
    LiveData<List<PlanMeal>> getAllPlanMeals();
    LiveData<PlanMeal> getPlanMealById(String id);
    LiveData<List<PlanMeal>> getPlanMealByDay(String day);
    void deleteMealFromPlan(PlanMeal meal);
}
