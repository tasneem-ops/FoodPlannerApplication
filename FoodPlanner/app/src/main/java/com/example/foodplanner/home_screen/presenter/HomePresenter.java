package com.example.foodplanner.home_screen.presenter;

import android.util.Log;

import com.example.foodplanner.home_screen.view.IViewHome;
import com.example.foodplanner.model.dto.Area;
import com.example.foodplanner.model.dto.Category;
import com.example.foodplanner.model.dto.InspirationMeal;
import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.repository.Repository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;

public class HomePresenter implements IHomePresenter {
    private static final String TAG = "HomePresenter";
    Repository repository;
    IViewHome view;

    Disposable  x , y, z;
    int c =0;
    public HomePresenter(IViewHome view, Repository repository) {
        this.repository = repository;
        this.view = view;
    }

    @Override
    public @NonNull Single<Meal> getMealOfTheDay(boolean isConnected) {
        Log.i(TAG, "getMealOfTheDay: Outside");
        if(isConnected){
            Log.i(TAG, "getMealOfTheDay: Connected");
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String date = dateFormat.format(calendar.getTime());
            x = repository.getInspirationMeal().observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mealList -> {
                        if(mealList.isEmpty()){
                            Log.i(TAG, "getMealOfTheDay: Empty");
                            y = repository.getMealOfTheDay().map(apiMealsList -> new Meal(apiMealsList.getMeals().get(0)))
                                    .subscribe(apiMeal ->{
                                        Log.i(TAG, "getMealOfTheDay: Inserting meal to db");
                                        z = repository.insertInspirationMeal(new InspirationMeal(apiMeal, date))
                                                .subscribe(()->{
                                                    Log.i(TAG, "getMealOfTheDay: On Complete");
                                                }, error->{
                                                    Log.i(TAG, "getMealOfTheDay: Error in db"+ error.getMessage());
                                                });
                                    });
                            view.setMealOfTheDay(repository.getMealOfTheDay().map(apiMealsList -> new Meal(apiMealsList.getMeals().get(0))));
                        }
                        else {
                            Log.i(TAG, "getMealOfTheDay: On Next");
                            c++;
                            if(mealList.get(0).getDate().equals(date)){
                                view.setMealOfTheDay(repository.getInspirationMeal().map(imeal -> new Meal(imeal.get(0))).firstOrError());
                            }
                            else{
                                repository.deleteAllInspirationMeals().observeOn(AndroidSchedulers.mainThread()).subscribe();
                                y = repository.getMealOfTheDay().map(apiMealsList -> new Meal(apiMealsList.getMeals().get(0)))
                                        .subscribe(apiMeal ->{
                                            repository.insertInspirationMeal(new InspirationMeal(apiMeal, date))
                                                    .observeOn(AndroidSchedulers.mainThread()).subscribe();
                                        });
                                view.setMealOfTheDay(repository.getMealOfTheDay().map(apiMealsList -> new Meal(apiMealsList.getMeals().get(0))));
                            }
                        }
                    });
        }
        else{
            Log.i(TAG, "getMealOfTheDay: Not Connected");
            view.setMealOfTheDay(repository.getInspirationMeal().map(mealList -> new Meal(mealList.get(0))).firstOrError());
        }
        Log.i(TAG, "getMealOfTheDay: ");
        return repository.getMealOfTheDay().map(apiMealsList -> new Meal(apiMealsList.getMeals().get(0)));
    }

    @Override
    public @NonNull Single<List<Category>> getCategoryList() {
        return repository.getCategoryList().map(categoryList -> categoryList.getCategories());
    }

    @Override
    public @NonNull Single<List<Area>> getAreaList() {
        return repository.getAreaList().map(areaList -> areaList.getAreas());
    }

    @Override
    public void unregisterView() {
        if(x != null && !x.isDisposed()){
            x.dispose();
        }
        if(y != null && !y.isDisposed()){
            y.dispose();
        }
        if(z != null && !z.isDisposed()){
            z.dispose();
        }
    }
}
