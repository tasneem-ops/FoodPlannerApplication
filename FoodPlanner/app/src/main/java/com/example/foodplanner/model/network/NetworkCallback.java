package com.example.foodplanner.model.network;

import com.example.foodplanner.model.dto.ApiFilteredMeal;
import com.example.foodplanner.model.dto.Area;
import com.example.foodplanner.model.dto.Category;
import com.example.foodplanner.model.dto.Meal;

import java.util.List;

public interface NetworkCallback{
    public interface MealDetailNetworkCallback{
        public void onMealDetailSuccessResult(Meal meal);
        public void onFailureResult(String errorMessage);
    }

    public interface CategoryListNetworkCallback{
        public void onCategoryListSuccessResult(List<Category> categoryList);
        public void onFailureResult(String errorMessage);
    }
    public interface AreaListNetworkCallback{
        public void onAreaListSuccessResult(List<Area> categoryList);
        public void onFailureResult(String errorMessage);
    }
    public interface FilterByCategoryNetworkCallback{
        public void onFilterByCategorySuccessResult(List<ApiFilteredMeal> categoryList);
        public void onFailureResult(String errorMessage);
    }
    public interface FilterByAreaNetworkCallback{
        public void onFilterByAreaSuccessResult(List<ApiFilteredMeal> categoryList);
        public void onFailureResult(String errorMessage);
    }
    public interface FilterByIngredientNetworkCallback{
        public void onFilterByIngredientSuccessResult(List<ApiFilteredMeal> categoryList);
        public void onFailureResult(String errorMessage);
    }
}
