package com.example.foodplanner.model.network;

import com.example.foodplanner.model.dto.ApiFilteredMealsList;
import com.example.foodplanner.model.dto.ApiIngridientList;
import com.example.foodplanner.model.dto.ApiMealsList;
import com.example.foodplanner.model.dto.AreaList;
import com.example.foodplanner.model.dto.CategoryList;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIRemoteDataSource implements IApiRemoteDataSource{
    private static APIRemoteDataSource instance;
    Retrofit retrofit;
    APIInterface apiService;
    private APIRemoteDataSource(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(APIInterface.class);
    }
    public static APIRemoteDataSource getInstance(){
        if(instance == null){
            instance = new APIRemoteDataSource();
        }
        return instance;
    }
    @Override
    public Single<ApiMealsList> getRandomMealNetworkCall() {
        return apiService.getRandomMeal();
    }
    @Override
    public Single<ApiMealsList> getMealByIdNetworkCall( String id) {
        return apiService.getMealById(id);
    }
    @Override
    public Single<CategoryList> getCategoryListNetworkCall() {
        return apiService.getCategoryList();
    }
    @Override
    public Single<AreaList> getAreaListNetworkCall() {
        return apiService.getCountriesList();
    }
    @Override
    public Single<ApiIngridientList> getIngredientListNetworkCall() {
        return apiService.getIngredientList();
    }
    @Override
    public Single<ApiFilteredMealsList> filterMealsByCategoryNetworkCall(String category) {
        return apiService.filterMealsByCategory(category);
    }
    @Override
    public Single<ApiFilteredMealsList> filterMealsByCountryNetworkCall(String country) {
        return apiService.filterMealsByArea(country);
    }
    @Override
    public Single<ApiFilteredMealsList> filterMealsByMainIngredientNetworkCall(String mainIngredient) {
        return apiService.filterMealsByMainIngredient(mainIngredient);
    }
    @Override
    public void getDataFromFirebase(String userID) {

    }

    @Override
    public void updateDataOnFirebase(String userID) {

    }


}
