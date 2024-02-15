package com.example.foodplanner.favorites.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.favorites.view.IViewFav;
import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.repository.Repository;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class FavPresenter implements IFavPresenter{
    IViewFav view;
    Repository repository;

    public FavPresenter(IViewFav view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void getFavMeals() {
        LiveData<List<Meal>> meals =  repository.getAllFavorite(FirebaseAuth.getInstance().getCurrentUser().getUid());
        view.setFavList(meals);
    }

    @Override
    public void deleteFavMeal(Meal meal) {
        repository.deleteMealFromFav(meal);
    }
}
