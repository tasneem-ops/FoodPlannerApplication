package com.example.foodplanner.model.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.dto.PlanMeal;

import java.util.List;

public class LocalDataSource implements ILocalDataSource{
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
    public LiveData<List<Meal>> getAllFavorite() {
        return favoritesDao.getAllFavorites();
    }

    @Override
    public LiveData<Meal> getFavMealById(String id) {
        return favoritesDao.getMealById(id);
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
    public LiveData<List<PlanMeal>> getAllPlanMeals() {
        return planDao.getAllPlanMeals();
    }

    @Override
    public LiveData<PlanMeal> getPlanMealById(String id) {
        return planDao.getMealById(id);
    }

    @Override
    public LiveData<List<PlanMeal>> getPlanMealByDay(String day) {
        return planDao.getMealByDay(day);
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
