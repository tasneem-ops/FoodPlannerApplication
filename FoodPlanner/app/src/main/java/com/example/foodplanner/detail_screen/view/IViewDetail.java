package com.example.foodplanner.detail_screen.view;

import com.example.foodplanner.model.dto.Meal;

public interface IViewDetail {
    void setMeal(Meal meal);
    void showError(String errMessage);
}
