package com.example.foodplanner.model.network;

import com.example.foodplanner.model.dto.ApiFilteredMeal;
import com.example.foodplanner.model.dto.ApiFilteredMealsList;
import com.example.foodplanner.model.dto.ApiIngridientList;
import com.example.foodplanner.model.dto.ApiMealsList;
import com.example.foodplanner.model.dto.Area;
import com.example.foodplanner.model.dto.AreaList;
import com.example.foodplanner.model.dto.Category;
import com.example.foodplanner.model.dto.CategoryList;
import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.network.NetworkCallback.*;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
    public void getRandomMealNetworkCall(MealDetailNetworkCallback networkCallback) {
        Call<ApiMealsList> call = apiService.getRandomMeal();
        Callback callback = new Callback<ApiMealsList>() {
            @Override
            public void onResponse(Call<ApiMealsList> call, Response<ApiMealsList> response) {
                networkCallback.onMealDetailSuccessResult(new Meal(response.body().getMeals().get(0)));
            }

            @Override
            public void onFailure(Call<ApiMealsList> call, Throwable t) {
                networkCallback.onFailureResult(t.getMessage());
            }
        };
        call.enqueue(callback);
    }

    @Override
    public void getMealByIdNetworkCall(MealDetailNetworkCallback networkCallback, String id) {
        Call<ApiMealsList> call = apiService.getMealById(id);
        Callback callback = new Callback<ApiMealsList>() {
            @Override
            public void onResponse(Call<ApiMealsList> call, Response<ApiMealsList> response) {
                networkCallback.onMealDetailSuccessResult(new Meal(response.body().getMeals().get(0)));
            }

            @Override
            public void onFailure(Call<ApiMealsList> call, Throwable t) {
                networkCallback.onFailureResult(t.getMessage());
            }
        };
        call.enqueue(callback);
    }

    @Override
    public void getCategoryListNetworkCall(CategoryListNetworkCallback networkCallback) {
        Call<CategoryList> call = apiService.getCategoryList();
        Callback callback = new Callback<CategoryList>(){

            @Override
            public void onResponse(Call<CategoryList> call, Response<CategoryList> response) {
                networkCallback.onCategoryListSuccessResult(response.body().getCategories());
            }

            @Override
            public void onFailure(Call<CategoryList> call, Throwable t) {
                networkCallback.onFailureResult(t.getMessage());
            }
        };
        call.enqueue(callback);
    }

    @Override
    public void getAreaListNetworkCall(AreaListNetworkCallback networkCallback) {
        Call<AreaList> call = apiService.getCountriesList();
        Callback callback = new Callback<AreaList>(){

            @Override
            public void onResponse(Call<AreaList> call, Response<AreaList> response) {
                networkCallback.onAreaListSuccessResult(response.body().getAreas());
            }

            @Override
            public void onFailure(Call<AreaList> call, Throwable t) {
                networkCallback.onFailureResult(t.getMessage());
            }
        };
        call.enqueue(callback);
    }

    @Override
    public void getIngredientListNetworkCall(IngredientListNetworkCallback networkCallback) {
        Call<ApiIngridientList> call = apiService.getIngredientList();
        Callback callback = new Callback<ApiIngridientList>(){

            @Override
            public void onResponse(Call<ApiIngridientList> call, Response<ApiIngridientList> response) {
                networkCallback.onIngredientListSuccessResult(response.body().getIngridientList());
            }

            @Override
            public void onFailure(Call<ApiIngridientList> call, Throwable t) {
                networkCallback.onFailureResult(t.getMessage());
            }
        };
        call.enqueue(callback);
    }

    @Override
    public void filterMealsByCategoryNetworkCall(FilterNetworkCallback networkCallback, String category) {
        Call<ApiFilteredMealsList> call = apiService.filterMealsByCategory(category);
        Callback callback = new Callback<ApiFilteredMealsList>() {
            @Override
            public void onResponse(Call<ApiFilteredMealsList> call, Response<ApiFilteredMealsList> response) {
                networkCallback.onFilterSuccessResult(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<ApiFilteredMealsList> call, Throwable t) {
                networkCallback.onFailureResult(t.getMessage());
            }
        };
        call.enqueue(callback);
    }

    @Override
    public void filterMealsByCountryNetworkCall(FilterNetworkCallback networkCallback, String country) {
        Call<ApiFilteredMealsList> call = apiService.filterMealsByArea(country);
        Callback callback = new Callback<ApiFilteredMealsList>() {
            @Override
            public void onResponse(Call<ApiFilteredMealsList> call, Response<ApiFilteredMealsList> response) {
                networkCallback.onFilterSuccessResult(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<ApiFilteredMealsList> call, Throwable t) {
                networkCallback.onFailureResult(t.getMessage());
            }
        };
        call.enqueue(callback);
    }

    @Override
    public void filterMealsByMainIngredientNetworkCall(FilterNetworkCallback networkCallback, String mainIngredient) {
        Call<ApiFilteredMealsList> call = apiService.filterMealsByMainIngredient(mainIngredient);
        Callback callback = new Callback<ApiFilteredMealsList>() {
            @Override
            public void onResponse(Call<ApiFilteredMealsList> call, Response<ApiFilteredMealsList> response) {
                networkCallback.onFilterSuccessResult(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<ApiFilteredMealsList> call, Throwable t) {
                networkCallback.onFailureResult(t.getMessage());
            }
        };
        call.enqueue(callback);
    }

    @Override
    public void getDataFromFirebase(String userID) {

    }

    @Override
    public void updateDataOnFirebase(String userID) {

    }


}
