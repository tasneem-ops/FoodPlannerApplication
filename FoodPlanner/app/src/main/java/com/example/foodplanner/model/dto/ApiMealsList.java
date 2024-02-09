package com.example.foodplanner.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiMealsList {
    @SerializedName("meals")
    public List<ApiMeal> meals;

    public List<ApiMeal> getMeals() {
        return meals;
    }

    public void setMeals(List<ApiMeal> meals) {
        this.meals = meals;
    }
}
