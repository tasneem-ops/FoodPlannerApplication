package com.example.foodplanner.detail_screen.presenter;

import android.util.Log;

import com.example.foodplanner.detail_screen.view.IViewDetail;
import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.dto.PlanMeal;
import com.example.foodplanner.model.repository.Repository;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;

public class DetailPresenter implements IDetailPresenter{
    Repository repository;
    IViewDetail view;
    Disposable x, y;

    public DetailPresenter(IViewDetail view, Repository repository) {
        this.repository = repository;
        this.view = view;
    }
    @Override
    public void getMealDetails(String id, boolean isConnected){
        if(isConnected){
            view.setMeal(repository.getMealById(id).map(apiMealsList -> new Meal(apiMealsList.getMeals().get(0))));
        }
        else{
            view.setMeal(repository.getFavMealById(id, FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .firstOrError());
            view.setMeal(repository.getPlanMealById(id, FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .map(planMeal -> new Meal(planMeal)).firstOrError());
        }
    }

    @Override
    public Completable addMealToFav(Meal meal){
        return repository.insertMealToFav(meal);
    }

    @Override
    public Completable addMealToPlan(Meal meal, String day){
        return repository.insertMealToPlan(new PlanMeal(meal, day));
    }

    @Override
    public Completable deleteMealFromFav(Meal meal) {
        return repository.deleteMealFromFav(meal);
    }

    @Override
    public void unregisterView() {
        if(x != null && !x.isDisposed()){
            x.dispose();
        }
        if(y != null && !y.isDisposed()){
            y.dispose();
        }
    }

    @Override
    public void isFav(String id) {
        y = repository.getFavMealById(id, FirebaseAuth.getInstance().getCurrentUser().getUid())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item->{
                    view.setFav();
                });
    }

}
