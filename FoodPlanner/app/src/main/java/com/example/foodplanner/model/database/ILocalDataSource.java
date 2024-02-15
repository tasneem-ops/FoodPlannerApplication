package com.example.foodplanner.model.database;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.dto.PlanMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface ILocalDataSource {
    void insertMealToFav(Meal meal);
    void insertAllMealsToFav(Meal... meal);
    Flowable<List<Meal>> getAllFavorite(String userID);
    Flowable<Meal> getFavMealById(String id, String userID);
    void deleteMealFromFav(Meal meal);

    void insertMealToPlan(PlanMeal meal);
    void insertAllMealsToPlan(PlanMeal... meals);
    Flowable<List<PlanMeal>> getAllPlanMeals(String userID);
    Flowable<PlanMeal> getPlanMealById(String id, String userID);
    Flowable<List<PlanMeal>> getPlanMealByDay(String day, String userID);
    void deleteMealFromPlan(PlanMeal meal);
}
