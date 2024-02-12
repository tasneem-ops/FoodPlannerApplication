package com.example.foodplanner.detail_screen.presenter;

import com.example.foodplanner.detail_screen.view.IViewDetail;
import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.network.NetworkCallback;
import com.example.foodplanner.model.repository.Repository;

public class DetailPresenter implements IDetailPresenter, NetworkCallback.MealDetailNetworkCallback {
    Repository repository;
    IViewDetail view;

    public DetailPresenter(IViewDetail view, Repository repository) {
        this.repository = repository;
        this.view = view;
    }
    @Override
    public void getMealDetails(String id){
        repository.getMealById(this, id);
    }

    @Override
    public void addMealToFav(Meal meal){

    }

    @Override
    public void addMealToPlan(Meal meal, String day){

    }

    @Override
    public void onMealDetailSuccessResult(Meal meal) {
        view.setMeal(meal);
    }

    @Override
    public void onFailureResult(String errorMessage) {
        view.showError(errorMessage);
    }
}
