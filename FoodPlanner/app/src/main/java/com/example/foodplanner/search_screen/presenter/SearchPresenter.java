package com.example.foodplanner.search_screen.presenter;

import android.util.Log;

import com.example.foodplanner.detail_screen.view.IViewDetail;
import com.example.foodplanner.model.dto.ApiFilteredMeal;
import com.example.foodplanner.model.dto.ApiIngridient;
import com.example.foodplanner.model.dto.Area;
import com.example.foodplanner.model.dto.Category;
import com.example.foodplanner.model.dto.SearchItem;
import com.example.foodplanner.model.repository.Repository;
import com.example.foodplanner.search_screen.view.IViewSearch;
import com.example.foodplanner.model.network.NetworkCallback.*;
import com.example.foodplanner.search_screen.view.OnSearchClickListener;
import com.example.foodplanner.search_screen.view.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchPresenter implements ISearchPresenter, CategoryListNetworkCallback,
         AreaListNetworkCallback, IngredientListNetworkCallback, FilterNetworkCallback{
    Repository repository;
    IViewSearch view;

    public SearchPresenter(IViewSearch view, Repository repository) {
        this.repository = repository;
        this.view = view;
    }

    @Override
    public void getCategoryList() {
        repository.getCategoryList(this);
    }

    @Override
    public void getIngredientList() {
        repository.getIngredientList(this);
    }

    @Override
    public void getCountryList() {
        repository.getAreaList(this);
    }

    @Override
    public void getMealsList(String name, int type) {
        switch (type){
            case Types.CATEGORY:
                repository.filterMealsByCategory(this, name);
                break;
            case Types.COUNTRY:
                repository.filterMealsByCountry(this, name);
                break;
            case Types.INGREDIENT:
                repository.filterMealsByMainIngredient(this, name);
                break;
            case Types.MEAL:
                break;
        }

    }

    @Override
    public void onCategoryListSuccessResult(List<Category> categoryList) {
        List<SearchItem> searchItems = new ArrayList<>();
        categoryList.forEach(category -> {
            searchItems.add(new SearchItem(category.getCategoryName(), category.getThumbnailUrl(), category.getId()));
        });
        view.setCategoryList(searchItems);
    }

    @Override
    public void onAreaListSuccessResult(List<Area> areaList) {
        List<SearchItem> searchItems = new ArrayList<>();
        areaList.forEach(area -> {
            searchItems.add(new SearchItem(area.getName(), area.getThumbnailUrl(), "0"));
        });
        view.setCountryList(searchItems);
    }

    @Override
    public void onIngredientListSuccessResult(List<ApiIngridient> ingridientList) {
        List<SearchItem> searchItems = new ArrayList<>();
        ingridientList.forEach(ingredient -> {
            searchItems.add(new SearchItem(ingredient.getName(),
                    "https://www.themealdb.com/images/ingredients/"+ ingredient.getName() + "-Small.png", ingredient.getId()));
        });
        view.setIngredientList(searchItems);
        Log.i("Ingrident", "onIngredientListSuccessResult: " + searchItems);
    }

    @Override
    public void onFilterSuccessResult(List<ApiFilteredMeal> mealList) {
        List<SearchItem> searchItems = new ArrayList<>();
        if(mealList != null && !(mealList.equals(Collections.emptyList()))){
            mealList.forEach(meal -> {
                searchItems.add(new SearchItem(meal.getStrMeal(), meal.getStrMealThumb(), meal.getIdMeal()));
            });
        }
        view.setMealList(searchItems);
    }

    @Override
    public void onFailureResult(String errorMessage) {
        view.showError(errorMessage);
    }
}
