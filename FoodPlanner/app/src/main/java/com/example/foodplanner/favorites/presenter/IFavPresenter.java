package com.example.foodplanner.favorites.presenter;

import com.example.foodplanner.model.dto.Meal;

public interface IFavPresenter {
    void getFavMeals();
    void deleteFavMeal(Meal meal);
}
