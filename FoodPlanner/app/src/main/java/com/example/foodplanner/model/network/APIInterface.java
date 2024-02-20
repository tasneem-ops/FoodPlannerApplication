package com.example.foodplanner.model.network;

import com.example.foodplanner.model.dto.ApiFilteredMealsList;
import com.example.foodplanner.model.dto.ApiIngridientList;
import com.example.foodplanner.model.dto.ApiMealsList;
import com.example.foodplanner.model.dto.AreaList;
import com.example.foodplanner.model.dto.CategoryList;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("random.php")
    Single<ApiMealsList> getRandomMeal();
    @GET("lookup.php?")
    Single<ApiMealsList> getMealById(@Query("i") String id);
    @GET("categories.php")
    Single<CategoryList> getCategoryList();

    @GET("list.php?a=list")
    Single<AreaList> getCountriesList();
    @GET("list.php?i=list")
    Single<ApiIngridientList> getIngredientList();
    @GET("filter.php?")
    Single<ApiFilteredMealsList> filterMealsByCategory(@Query("c") String category);

    @GET("filter.php?")
    Single<ApiFilteredMealsList> filterMealsByMainIngredient(@Query("i") String mainIngredient);
    @GET("filter.php?")
    Single<ApiFilteredMealsList> filterMealsByArea(@Query("a")String area);
}
