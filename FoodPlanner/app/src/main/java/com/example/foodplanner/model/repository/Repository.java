package com.example.foodplanner.model.repository;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.database.ILocalDataSource;
import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.dto.PlanMeal;
import com.example.foodplanner.model.network.IApiRemoteDataSource;
import com.example.foodplanner.model.network.NetworkCallback;

import java.util.List;

public class Repository {
    ILocalDataSource localDataSource;
    IApiRemoteDataSource remoteDataSource;
    private static Repository instance;
    private Repository(ILocalDataSource localDataSource, IApiRemoteDataSource remoteDataSource){
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public static Repository getInstance(ILocalDataSource localDataSource, IApiRemoteDataSource remoteDataSource){
        if(instance == null){
            instance = new Repository(localDataSource, remoteDataSource);
        }
        return instance;
    }
    public void insertMealToFav(Meal meal){
        localDataSource.insertMealToFav(meal);
    }
    public void insertAllMealsToFav(Meal... meals){
        localDataSource.insertAllMealsToFav(meals);
    }
    public LiveData<List<Meal>> getAllFavorite(){
        return localDataSource.getAllFavorite();
    }
    public LiveData<Meal> getFavMealById(String id){
        return localDataSource.getFavMealById(id);
    }
    public void deleteMealFromFav(Meal meal){
        localDataSource.deleteMealFromFav(meal);
    }

    public void insertMealToPlan(PlanMeal meal){
        localDataSource.insertMealToPlan(meal);
    }
    public void insertAllMealsToPlan(PlanMeal... meals){
        localDataSource.insertAllMealsToPlan(meals);
    }
    public LiveData<List<PlanMeal>> getAllPlanMeals(){
        return localDataSource.getAllPlanMeals();
    }
    public LiveData<PlanMeal> getPlanMealById(String id){
        return localDataSource.getPlanMealById(id);
    }
    public LiveData<List<PlanMeal>> getPlanMealByDay(String day){
        return localDataSource.getPlanMealByDay(day);
    }
    public void deleteMealFromPlan(PlanMeal meal){
        localDataSource.deleteMealFromPlan(meal);
    }

    public void getMealOfTheDay(NetworkCallback.MealDetailNetworkCallback networkCallback){
        remoteDataSource.getRandomMealNetworkCall(networkCallback);
    }
    public void getMealById(NetworkCallback.MealDetailNetworkCallback networkCallback, String id){
        remoteDataSource.getMealByIdNetworkCall(networkCallback, id);
    }
    public void getCategoryList(NetworkCallback.CategoryListNetworkCallback networkCallback){
        remoteDataSource.getCategoryListNetworkCall(networkCallback);
    }
    public void getAreaList(NetworkCallback.AreaListNetworkCallback networkCallback){
        remoteDataSource.getAreaListNetworkCall(networkCallback);
    }
    public void filterMealsByCategory(NetworkCallback.FilterByCategoryNetworkCallback networkCallback, String category){
        remoteDataSource.filterMealsByCategoryNetworkCall(networkCallback, category);
    }
    public void filterMealsByCountry(NetworkCallback.FilterByAreaNetworkCallback networkCallback, String country){
        remoteDataSource.filterMealsByCountryNetworkCall(networkCallback, country);
    }
    public void filterMealsByMainIngredient(NetworkCallback.FilterByIngredientNetworkCallback networkCallback, String mainIngredient){
        remoteDataSource.filterMealsByMainIngredientNetworkCall(networkCallback, mainIngredient);
    }
}
