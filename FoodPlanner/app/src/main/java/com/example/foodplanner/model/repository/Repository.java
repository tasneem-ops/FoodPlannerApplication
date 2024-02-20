package com.example.foodplanner.model.repository;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.database.ILocalDataSource;
import com.example.foodplanner.model.dto.ApiFilteredMealsList;
import com.example.foodplanner.model.dto.ApiIngridientList;
import com.example.foodplanner.model.dto.ApiMealsList;
import com.example.foodplanner.model.dto.AreaList;
import com.example.foodplanner.model.dto.CategoryList;
import com.example.foodplanner.model.dto.InspirationMeal;
import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.dto.PlanMeal;
import com.example.foodplanner.model.network.IApiRemoteDataSource;

import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
    public Completable insertMealToFav(Meal meal){
        return localDataSource.insertMealToFav(meal).subscribeOn(Schedulers.io());
    }
    public Completable insertAllMealsToFav(Meal... meals){
        return localDataSource.insertAllMealsToFav(meals).subscribeOn(Schedulers.io());
    }
    public Flowable<List<Meal>> getAllFavorite(String userID){
        return localDataSource.getAllFavorite(userID).subscribeOn(Schedulers.io());
    }
    public Flowable<Meal> getFavMealById(String id, String userID){
        return localDataSource.getFavMealById(id, userID)
                .subscribeOn(Schedulers.io());
    }
    public Completable deleteMealFromFav(Meal meal){
        return localDataSource.deleteMealFromFav(meal).subscribeOn(Schedulers.io());
    }

    public Completable insertMealToPlan(PlanMeal meal){
        return localDataSource.insertMealToPlan(meal).subscribeOn(Schedulers.io());
    }
    public Completable insertAllMealsToPlan(PlanMeal... meals){
        return localDataSource.insertAllMealsToPlan(meals).subscribeOn(Schedulers.io());
    }
    public Flowable<List<PlanMeal>> getAllPlanMeals(String userID){
        return localDataSource.getAllPlanMeals(userID).subscribeOn(Schedulers.io());
    }
    public Flowable<PlanMeal> getPlanMealById(String id, String userID){
        return localDataSource.getPlanMealById(id, userID).subscribeOn(Schedulers.io());
    }
    public Flowable<List<PlanMeal>> getPlanMealByDay(String day, String userID){
        return localDataSource.getPlanMealByDay(day, userID).subscribeOn(Schedulers.io());
    }
    public Completable deleteMealFromPlan(PlanMeal meal){
        return localDataSource.deleteMealFromPlan(meal).subscribeOn(Schedulers.io());
    }

    public Single<ApiMealsList> getMealOfTheDay(){
        return remoteDataSource.getRandomMealNetworkCall().subscribeOn(Schedulers.io());
    }
    public Single<ApiMealsList> getMealById(String id){
        return remoteDataSource.getMealByIdNetworkCall(id).subscribeOn(Schedulers.io());
    }
    public Single<CategoryList> getCategoryList(){
        return remoteDataSource.getCategoryListNetworkCall().subscribeOn(Schedulers.io());
    }
    public Single<AreaList> getAreaList(){
        return remoteDataSource.getAreaListNetworkCall().subscribeOn(Schedulers.io());
    }
    public Single<ApiIngridientList> getIngredientList(){
        return remoteDataSource.getIngredientListNetworkCall().subscribeOn(Schedulers.io());
    }
    public Single<ApiFilteredMealsList> filterMealsByCategory(String category){
        return remoteDataSource.filterMealsByCategoryNetworkCall(category).subscribeOn(Schedulers.io());
    }
    public Single<ApiFilteredMealsList> filterMealsByCountry(String country){
        return remoteDataSource.filterMealsByCountryNetworkCall(country).subscribeOn(Schedulers.io());
    }
    public Single<ApiFilteredMealsList> filterMealsByMainIngredient(String mainIngredient){
        return remoteDataSource.filterMealsByMainIngredientNetworkCall(mainIngredient).subscribeOn(Schedulers.io());
    }
    public Completable insertInspirationMeal(InspirationMeal meal){
        return localDataSource.insertInspirationMeal(meal).subscribeOn(Schedulers.io());
    }
    public Flowable<List<InspirationMeal>> getInspirationMeal(){
        return localDataSource.getInspirationMeal()
                .switchIfEmpty(Flowable.just(Collections.emptyList()))
                .subscribeOn(Schedulers.io());
    }
    public Completable deleteAllInspirationMeals(){
        return localDataSource.deleteAllInspirationMeals().subscribeOn(Schedulers.io());
    }
}
