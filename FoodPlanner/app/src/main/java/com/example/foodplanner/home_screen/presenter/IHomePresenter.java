package com.example.foodplanner.home_screen.presenter;

import com.example.foodplanner.model.dto.ApiMeal;
import com.example.foodplanner.model.dto.Area;
import com.example.foodplanner.model.dto.Category;
import com.example.foodplanner.model.dto.Meal;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;

public interface IHomePresenter {
    @NonNull Single<Meal> getMealOfTheDay(boolean isConnected);
    @NonNull Single<List<Category>> getCategoryList();
    @NonNull Single<List<Area>> getAreaList();

    void unregisterView();
}