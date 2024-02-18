package com.example.foodplanner.detail_screen.view;

import com.example.foodplanner.model.dto.Meal;

import io.reactivex.rxjava3.core.Single;

public interface IViewDetail {
    void setMeal(Single<Meal> observableMeal);
    void showError(String errMessage);
    void setFav();
}
