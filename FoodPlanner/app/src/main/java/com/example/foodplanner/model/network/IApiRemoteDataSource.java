package com.example.foodplanner.model.network;

import com.example.foodplanner.model.dto.ApiFilteredMealsList;
import com.example.foodplanner.model.dto.ApiIngridientList;
import com.example.foodplanner.model.dto.ApiMealsList;
import com.example.foodplanner.model.dto.AreaList;
import com.example.foodplanner.model.dto.CategoryList;

import io.reactivex.rxjava3.core.Single;

public interface IApiRemoteDataSource {
    Single<ApiMealsList> getRandomMealNetworkCall();
    Single<ApiMealsList> getMealByIdNetworkCall(String id);
    Single<CategoryList> getCategoryListNetworkCall();
    Single<AreaList> getAreaListNetworkCall();
    Single<ApiIngridientList> getIngredientListNetworkCall();
    Single<ApiFilteredMealsList> filterMealsByCategoryNetworkCall(String category);
    Single<ApiFilteredMealsList> filterMealsByCountryNetworkCall(String country);
    Single<ApiFilteredMealsList> filterMealsByMainIngredientNetworkCall(String mainIngredient);
    void getDataFromFirebase(String userID);
    void updateDataOnFirebase(String userID);
}
