package com.example.foodplanner.model.network;

import com.example.foodplanner.model.dto.ApiFilteredMeal;
import com.example.foodplanner.model.dto.Area;
import com.example.foodplanner.model.dto.AreaList;
import com.example.foodplanner.model.dto.Category;
import com.example.foodplanner.model.dto.CategoryList;
import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.network.NetworkCallback.*;

import java.util.List;

public interface IApiRemoteDataSource {
    void getRandomMealNetworkCall(MealDetailNetworkCallback networkCallback);
    void getMealByIdNetworkCall(MealDetailNetworkCallback networkCallback, String id);
    void getCategoryListNetworkCall(CategoryListNetworkCallback networkCallback);
    void getAreaListNetworkCall(AreaListNetworkCallback networkCallback);
    void getIngredientListNetworkCall(IngredientListNetworkCallback networkCallback);
    void filterMealsByCategoryNetworkCall(FilterNetworkCallback networkCallback, String category);
    void filterMealsByCountryNetworkCall(FilterNetworkCallback networkCallback, String country);
    void filterMealsByMainIngredientNetworkCall(FilterNetworkCallback networkCallback, String mainIngredient);
    void getDataFromFirebase(String userID);
    void updateDataOnFirebase(String userID);
}
