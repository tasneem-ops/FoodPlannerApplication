package com.example.foodplanner.favorites.presenter;

import com.example.foodplanner.model.dto.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface IFavPresenter {
    Flowable<List<Meal>> getFavMeals();
    void deleteFavMeal(Meal meal);
}
