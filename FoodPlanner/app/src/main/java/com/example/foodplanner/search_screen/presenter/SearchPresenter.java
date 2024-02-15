package com.example.foodplanner.search_screen.presenter;

import com.example.foodplanner.model.dto.SearchItem;
import com.example.foodplanner.model.repository.Repository;
import com.example.foodplanner.search_screen.view.IViewSearch;
import com.example.foodplanner.search_screen.view.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;

public class SearchPresenter implements ISearchPresenter{
    Repository repository;
    IViewSearch view;

    public SearchPresenter(IViewSearch view, Repository repository) {
        this.repository = repository;
        this.view = view;
    }

    @Override
    public @NonNull Single<List<SearchItem>> getCategoryList() {
        return repository.getCategoryList().map(categoryList -> {
            List<SearchItem> searchItems = new ArrayList<>();
            categoryList.getCategories().forEach(category -> {
                searchItems.add(new SearchItem(category.getCategoryName(), category.getThumbnailUrl(), category.getId()));
            });
            return searchItems;
        });
    }

    @Override
    public @NonNull Single<List<SearchItem>> getIngredientList() {
        return repository.getIngredientList().map(apiIngridientList -> {
            List<SearchItem> searchItems = new ArrayList<>();
            apiIngridientList.getIngridientList().forEach(ingredient -> {
                searchItems.add(new SearchItem(ingredient.getName(),
                        "https://www.themealdb.com/images/ingredients/"+ ingredient.getName() + "-Small.png", ingredient.getId()));
            });
            return searchItems;
        });
    }

    @Override
    public @NonNull Single<List<SearchItem>> getCountryList() {
        return repository.getAreaList().map(areaList -> {
            List<SearchItem> searchItems = new ArrayList<>();
            areaList.getAreas().forEach(area -> {
                searchItems.add(new SearchItem(area.getName(), area.getThumbnailUrl(), "0"));
            });
            return searchItems;
        });
    }

    @Override
    public @NonNull Single<List<SearchItem>> getMealsList(String name, int type) {
        switch (type){
            case Types.CATEGORY:
                return repository.filterMealsByCategory(name).map(apiFilteredMealsList -> {
                    List<SearchItem> searchItems = new ArrayList<>();
                    if(apiFilteredMealsList != null && !(apiFilteredMealsList.equals(Collections.emptyList()))){
                        apiFilteredMealsList.getMeals().forEach(meal -> {
                            searchItems.add(new SearchItem(meal.getStrMeal(), meal.getStrMealThumb(), meal.getIdMeal()));
                        });
                    }
                    return searchItems;
                });
            case Types.COUNTRY:
                return repository.filterMealsByCountry(name).map(apiFilteredMealsList -> {
                List<SearchItem> searchItems = new ArrayList<>();
                if(apiFilteredMealsList != null && !(apiFilteredMealsList.equals(Collections.emptyList()))){
                    apiFilteredMealsList.getMeals().forEach(meal -> {
                        searchItems.add(new SearchItem(meal.getStrMeal(), meal.getStrMealThumb(), meal.getIdMeal()));
                    });
                }
                return searchItems;
            });
            case Types.INGREDIENT:
                return repository.filterMealsByMainIngredient(name).map(apiFilteredMealsList -> {
                    List<SearchItem> searchItems = new ArrayList<>();
                    if(apiFilteredMealsList != null && !(apiFilteredMealsList.equals(Collections.emptyList()))){
                        apiFilteredMealsList.getMeals().forEach(meal -> {
                            searchItems.add(new SearchItem(meal.getStrMeal(), meal.getStrMealThumb(), meal.getIdMeal()));
                        });
                    }
                    return searchItems;
                });
            case Types.MEAL:
                break;
        }
        return null;
    }

//    @Override
//    public void onCategoryListSuccessResult(List<Category> categoryList) {
//        List<SearchItem> searchItems = new ArrayList<>();
//        categoryList.forEach(category -> {
//            searchItems.add(new SearchItem(category.getCategoryName(), category.getThumbnailUrl(), category.getId()));
//        });
//        view.setCategoryList(searchItems);
//    }
//
//    @Override
//    public void onAreaListSuccessResult(List<Area> areaList) {
//        List<SearchItem> searchItems = new ArrayList<>();
//        areaList.forEach(area -> {
//            searchItems.add(new SearchItem(area.getName(), area.getThumbnailUrl(), "0"));
//        });
//        view.setCountryList(searchItems);
//    }
//
//    @Override
//    public void onIngredientListSuccessResult(List<ApiIngridient> ingridientList) {
//        List<SearchItem> searchItems = new ArrayList<>();
//        ingridientList.forEach(ingredient -> {
//            searchItems.add(new SearchItem(ingredient.getName(),
//                    "https://www.themealdb.com/images/ingredients/"+ ingredient.getName() + "-Small.png", ingredient.getId()));
//        });
//        view.setIngredientList(searchItems);
//        Log.i("Ingrident", "onIngredientListSuccessResult: " + searchItems);
//    }
//
//    @Override
//    public void onFilterSuccessResult(List<ApiFilteredMeal> mealList) {
//        List<SearchItem> searchItems = new ArrayList<>();
//        if(mealList != null && !(mealList.equals(Collections.emptyList()))){
//            mealList.forEach(meal -> {
//                searchItems.add(new SearchItem(meal.getStrMeal(), meal.getStrMealThumb(), meal.getIdMeal()));
//            });
//        }
//        view.setMealList(searchItems);
//    }
//
//    @Override
//    public void onFailureResult(String errorMessage) {
//        view.showError(errorMessage);
//    }
}
