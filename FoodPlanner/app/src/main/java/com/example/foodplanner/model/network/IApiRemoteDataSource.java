package com.example.foodplanner.model.network;

import com.example.foodplanner.model.dto.ApiFilteredMeal;
import com.example.foodplanner.model.dto.Area;
import com.example.foodplanner.model.dto.AreaList;
import com.example.foodplanner.model.dto.Category;
import com.example.foodplanner.model.dto.CategoryList;
import com.example.foodplanner.model.dto.Meal;

import java.util.List;

public interface IApiRemoteDataSource {
    void getRandomMealNetworkCall(NetworkCallback.MealDetailNetworkCallback networkCallback);
    void getMealByIdNetworkCall(NetworkCallback.MealDetailNetworkCallback networkCallback, String id);
    void getCategoryListNetworkCall(NetworkCallback.CategoryListNetworkCallback networkCallback);
    void getAreaListNetworkCall(NetworkCallback.AreaListNetworkCallback networkCallback);
    void filterMealsByCategoryNetworkCall(NetworkCallback.FilterByCategoryNetworkCallback networkCallback, String category);
    void filterMealsByCountryNetworkCall(NetworkCallback.FilterByAreaNetworkCallback networkCallback, String country);
    void filterMealsByMainIngredientNetworkCall(NetworkCallback.FilterByIngredientNetworkCallback networkCallback, String mainIngredient);
}
