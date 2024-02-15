package com.example.foodplanner.detail_screen.presenter;

import com.example.foodplanner.model.dto.Meal;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;

public interface IDetailPresenter {
    @NonNull Single<Meal> getMealDetails(String id);

    void addMealToFav(Meal meal);

    void addMealToPlan(Meal meal, String day);
}
