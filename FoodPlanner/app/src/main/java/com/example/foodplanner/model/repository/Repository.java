package com.example.foodplanner.model.repository;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.database.ILocalDataSource;
import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.dto.PlanMeal;
import com.example.foodplanner.model.network.IApiRemoteDataSource;
import com.example.foodplanner.model.network.NetworkCallback;
import com.example.foodplanner.model.network.NetworkCallback.*;
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
    public LiveData<List<Meal>> getAllFavorite(String userID){
        return localDataSource.getAllFavorite(userID);
    }
    public LiveData<Meal> getFavMealById(String id, String userID){
        return localDataSource.getFavMealById(id, userID);
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
    public LiveData<List<PlanMeal>> getAllPlanMeals(String userID){
        return localDataSource.getAllPlanMeals(userID);
    }
    public LiveData<PlanMeal> getPlanMealById(String id, String userID){
        return localDataSource.getPlanMealById(id, userID);
    }
    public LiveData<List<PlanMeal>> getPlanMealByDay(String day, String userID){
        return localDataSource.getPlanMealByDay(day, userID);
    }
    public void deleteMealFromPlan(PlanMeal meal){
        localDataSource.deleteMealFromPlan(meal);
    }

    public void getMealOfTheDay(MealDetailNetworkCallback networkCallback){
        remoteDataSource.getRandomMealNetworkCall(networkCallback);
    }
    public void getMealById(MealDetailNetworkCallback networkCallback, String id){
        remoteDataSource.getMealByIdNetworkCall(networkCallback, id);
    }
    public void getCategoryList(CategoryListNetworkCallback networkCallback){
        remoteDataSource.getCategoryListNetworkCall(networkCallback);
    }
    public void getAreaList(AreaListNetworkCallback networkCallback){
        remoteDataSource.getAreaListNetworkCall(networkCallback);
    }
    public void getIngredientList(IngredientListNetworkCallback networkCallback){
        remoteDataSource.getIngredientListNetworkCall(networkCallback);
    }
    public void filterMealsByCategory(FilterNetworkCallback networkCallback, String category){
        remoteDataSource.filterMealsByCategoryNetworkCall(networkCallback, category);
    }
    public void filterMealsByCountry(FilterNetworkCallback networkCallback, String country){
        remoteDataSource.filterMealsByCountryNetworkCall(networkCallback, country);
    }
    public void filterMealsByMainIngredient(FilterNetworkCallback networkCallback, String mainIngredient){
        remoteDataSource.filterMealsByMainIngredientNetworkCall(networkCallback, mainIngredient);
    }
}
