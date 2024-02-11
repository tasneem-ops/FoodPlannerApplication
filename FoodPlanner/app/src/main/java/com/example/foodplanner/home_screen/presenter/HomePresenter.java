package com.example.foodplanner.home_screen.presenter;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.home_screen.view.HomeFragment;
import com.example.foodplanner.home_screen.view.IViewHome;
import com.example.foodplanner.model.dto.Area;
import com.example.foodplanner.model.dto.Category;
import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.network.NetworkCallback;
import com.example.foodplanner.model.repository.Repository;

import java.util.List;

public class HomePresenter implements IHomePresenter, NetworkCallback.MealDetailNetworkCallback, NetworkCallback.CategoryListNetworkCallback, NetworkCallback.AreaListNetworkCallback {
    Repository repository;
    IViewHome view;

    public HomePresenter(IViewHome view, Repository repository) {
        this.repository = repository;
        this.view = view;
    }

    @Override
    public void getMealOfTheDay() {
        repository.getMealOfTheDay(this);
    }

    @Override
    public void getCategoryList() {
        repository.getCategoryList(this);
    }

    @Override
    public void getAreaList() {
        repository.getAreaList(this);
    }

    @Override
    public void onMealDetailSuccessResult(Meal meal) {
        view.setMealOfTheDay(meal);
    }

    @Override
    public void onCategoryListSuccessResult(List<Category> categoryList) {
        view.setCategoryList(categoryList);
    }

    @Override
    public void onAreaListSuccessResult(List<Area> areaList) {
        view.setAreaList(areaList);
    }

    @Override
    public void onFailureResult(String errorMessage) {
        view.showError(errorMessage);
    }
}
