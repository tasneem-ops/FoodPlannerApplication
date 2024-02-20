package com.example.foodplanner.model.database;

import android.content.Context;

import com.example.foodplanner.model.dto.InspirationMeal;
import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.dto.PlanMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class  LocalDataSource implements ILocalDataSource{
    Context context;
    FavoritesDao favoritesDao;
    PlanDao planDao;
    InspirationMealDao inspirationMealDao;
    FoodPlannerDatabase db;

    private static LocalDataSource instance = null;
    private LocalDataSource(Context context){
        this.context = context;
        db =FoodPlannerDatabase.getInstance(context);
        favoritesDao = db.getFavoritesDao();
        planDao = db.getPlanDao();
        inspirationMealDao = db.getInspirationalMealDao();
    }
    public static LocalDataSource getInstance(Context context){
        if(instance == null){
            instance = new LocalDataSource(context);
        }
        return instance;
    }
    @Override
    public Completable insertMealToFav(Meal meal) {
        return favoritesDao.insertMeal(meal);
    }

    @Override
    public Completable insertAllMealsToFav(Meal... meals) {
        return favoritesDao.insertAllMeals(meals);
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
    public Completable deleteMealFromFav(Meal meal) {
        return favoritesDao.deleteMeal(meal);
    }

    @Override
    public Completable insertMealToPlan(PlanMeal meal) {
        return planDao.insertMeal(meal);
    }

    @Override
    public Completable insertAllMealsToPlan(PlanMeal... meals) {
        return planDao.insertAllMeals(meals);
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
    public Completable deleteMealFromPlan(PlanMeal meal) {
        return planDao.deleteMeal(meal);
    }

    @Override
    public Completable insertInspirationMeal(InspirationMeal meal) {
        return inspirationMealDao.insertMeal(meal);
    }

    @Override
    public Flowable<List<InspirationMeal>> getInspirationMeal() {
        return inspirationMealDao.getMeal();
    }

    @Override
    public Completable deleteAllInspirationMeals() {
        return inspirationMealDao.deleteAll();
    }
}
