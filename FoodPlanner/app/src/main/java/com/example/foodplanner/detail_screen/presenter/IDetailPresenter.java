package com.example.foodplanner.detail_screen.presenter;

import com.example.foodplanner.model.dto.Meal;

public interface IDetailPresenter {
    void getMealDetails(String id);

    void addMealToFav(Meal meal);

    void addMealToPlan(Meal meal, String day);
}
