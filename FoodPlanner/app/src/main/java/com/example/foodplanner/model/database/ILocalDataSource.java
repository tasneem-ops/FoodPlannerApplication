package com.example.foodplanner.model.database;

import com.example.foodplanner.model.dto.InspirationMeal;
import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.dto.PlanMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface ILocalDataSource {
    Completable insertMealToFav(Meal meal);
    Completable insertAllMealsToFav(Meal... meal);
    Flowable<List<Meal>> getAllFavorite(String userID);
    Flowable<Meal> getFavMealById(String id, String userID);
    Completable deleteMealFromFav(Meal meal);

    Completable insertMealToPlan(PlanMeal meal);
    Completable insertAllMealsToPlan(PlanMeal... meals);
    Flowable<List<PlanMeal>> getAllPlanMeals(String userID);
    Flowable<PlanMeal> getPlanMealById(String id, String userID);
    Flowable<List<PlanMeal>> getPlanMealByDay(String day, String userID);
    Completable deleteMealFromPlan(PlanMeal meal);

    Completable insertInspirationMeal(InspirationMeal meal);
    Flowable<List<InspirationMeal>> getInspirationMeal();
    Completable deleteAllInspirationMeals();
}
