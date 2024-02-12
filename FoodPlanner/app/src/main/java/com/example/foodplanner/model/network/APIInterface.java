package com.example.foodplanner.model.network;

import com.example.foodplanner.model.dto.ApiFilteredMealsList;
import com.example.foodplanner.model.dto.ApiIngridientList;
import com.example.foodplanner.model.dto.ApiMealsList;
import com.example.foodplanner.model.dto.AreaList;
import com.example.foodplanner.model.dto.CategoryList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("random.php")
    Call<ApiMealsList> getRandomMeal();
    @GET("lookup.php?")
    Call<ApiMealsList> getMealById(@Query("i") String id);
    @GET("categories.php")
    Call<CategoryList> getCategoryList();

    @GET("list.php?a=list")
    Call<AreaList> getCountriesList();
    @GET("list.php?i=list")
    Call<ApiIngridientList> getIngredientList();
    @GET("filter.php?")
    Call<ApiFilteredMealsList> filterMealsByCategory(@Query("c") String category);

    @GET("filter.php?")
    Call<ApiFilteredMealsList> filterMealsByMainIngredient(@Query("i") String mainIngredient);
    @GET("filter.php?")
    Call<ApiFilteredMealsList> filterMealsByArea(@Query("a")String area);
}
