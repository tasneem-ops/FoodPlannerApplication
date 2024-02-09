package com.example.foodplanner.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiFilteredMealsList {
    @SerializedName("meals")
    private List<ApiFilteredMeal> meals;

    public List<ApiFilteredMeal> getMeals() {
        return meals;
    }

    public void setMeals(List<ApiFilteredMeal> meals) {
        this.meals = meals;
    }
}
