package com.example.foodplanner.detail_screen.presenter;

import com.example.foodplanner.model.dto.Meal;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface IDetailPresenter {
    void getMealDetails(String id, boolean isConnected);

    Completable addMealToFav(Meal meal);

    Completable addMealToPlan(Meal meal, String day);
    Completable deleteMealFromFav(Meal meal);
    void unregisterView();
    void isFav(String id);
}
