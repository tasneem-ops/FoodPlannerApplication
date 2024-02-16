package com.example.foodplanner.model.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.dto.PlanMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class  LocalDataSource implements ILocalDataSource{
    Context context;
    FavoritesDao favoritesDao;
    PlanDao planDao;
    FoodPlannerDatabase db;

    private static LocalDataSource instance = null;
    private LocalDataSource(Context context){
        this.context = context;
        db =FoodPlannerDatabase.getInstance(context);
        favoritesDao = db.getFavoritesDao();
        planDao = db.getPlanDao();
    }
    public static LocalDataSource getInstance(Context context){
        if(instance == null){
            instance = new LocalDataSource(context);
        }
        return instance;
    }
    @Override
    public void insertMealToFav(Meal meal) {
        new Thread(){
            @Override
            public void run() {
                favoritesDao.insertMeal(meal);
            }
        }.start();
    }

    @Override
    public void insertAllMealsToFav(Meal... meals) {
        new Thread(){
            @Override
            public void run() {
                favoritesDao.insertAllMeals(meals);
            }
        }.start();
    }

    @Override
    public Flowable<List<Meal>> getAllFavorite(String userID) {
        return favoritesDao.getAllFavorites(userID);
    }

    @Override
    public Flowable<Meal> getFavMealById(String id, String userID) {
        return favoritesDao.getMealById(id, userID);
    }

    @Override
    public void deleteMealFromFav(Meal meal) {
        new Thread(){
            @Override
            public void run() {
                favoritesDao.deleteMeal(meal);
            }
        }.start();
    }

    @Override
    public void insertMealToPlan(PlanMeal meal) {
        new Thread(){
            @Override
            public void run() {
                planDao.insertMeal(meal);
            }
        }.start();
    }

    @Override
    public void insertAllMealsToPlan(PlanMeal... meals) {
        new Thread(){
            @Override
            public void run() {
                planDao.insertAllMeals(meals);
            }
        }.start();
    }

    @Override
    public Flowable<List<PlanMeal>> getAllPlanMeals(String userID) {
        return planDao.getAllPlanMeals(userID);
    }

    @Override
    public Flowable<PlanMeal> getPlanMealById(String id, String userID) {
        return planDao.getMealById(id, userID);
    }

    @Override
    public Flowable<List<PlanMeal>> getPlanMealByDay(String day, String userID) {
        return planDao.getMealByDay(day, userID);
    }

    @Override
    public void deleteMealFromPlan(PlanMeal meal) {
        new Thread(){
            @Override
            public void run() {
                planDao.deleteMeal(meal);
            }
        }.start();
    }
}
