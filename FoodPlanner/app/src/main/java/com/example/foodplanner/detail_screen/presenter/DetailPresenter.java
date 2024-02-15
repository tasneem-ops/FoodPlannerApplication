package com.example.foodplanner.detail_screen.presenter;

import com.example.foodplanner.detail_screen.view.IViewDetail;
import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.dto.PlanMeal;
import com.example.foodplanner.model.repository.Repository;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;

public class DetailPresenter implements IDetailPresenter{
    Repository repository;
    IViewDetail view;

    public DetailPresenter(IViewDetail view, Repository repository) {
        this.repository = repository;
        this.view = view;
    }
    @Override
    public @NonNull Single<Meal> getMealDetails(String id){
        return repository.getMealById(id).map(apiMealsList -> new Meal(apiMealsList.getMeals().get(0)));
    }

    @Override
    public void addMealToFav(Meal meal){
        repository.insertMealToFav(meal);
    }

    @Override
    public void addMealToPlan(Meal meal, String day){
        repository.insertMealToPlan(new PlanMeal(meal, day));
    }
}
