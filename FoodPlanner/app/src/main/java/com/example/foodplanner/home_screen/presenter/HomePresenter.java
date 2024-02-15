package com.example.foodplanner.home_screen.presenter;

import com.example.foodplanner.home_screen.view.IViewHome;
import com.example.foodplanner.model.dto.Area;
import com.example.foodplanner.model.dto.Category;
import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.repository.Repository;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;

public class HomePresenter implements IHomePresenter {
    Repository repository;
    IViewHome view;

    public HomePresenter(IViewHome view, Repository repository) {
        this.repository = repository;
        this.view = view;
    }

    @Override
    public @NonNull Single<Meal> getMealOfTheDay() {
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

//    @Override
//    public void onMealDetailSuccessResult(Meal meal) {
//        view.setMealOfTheDay(meal);
//    }
//
//    @Override
//    public void onCategoryListSuccessResult(List<Category> categoryList) {
//        view.setCategoryList(categoryList);
//    }
//
//    @Override
//    public void onAreaListSuccessResult(List<Area> areaList) {
//        view.setAreaList(areaList);
//    }
//
//    @Override
//    public void onFailureResult(String errorMessage) {
//        view.showError(errorMessage);
//    }
}
