package com.example.foodplanner.favorites.presenter;

import com.example.foodplanner.favorites.view.IViewFav;
import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.repository.Repository;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class FavPresenter implements IFavPresenter{
    IViewFav view;
    Repository repository;

    public FavPresenter(IViewFav view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public Flowable<List<Meal>> getFavMeals() {
        Flowable<List<Meal>> meals =  repository.getAllFavorite(FirebaseAuth.getInstance().getCurrentUser().getUid());
        return meals;
//        view.setFavList(meals);
    }

    @Override
    public Completable deleteFavMeal(Meal meal) {
        return repository.deleteMealFromFav(meal);
    }
}
