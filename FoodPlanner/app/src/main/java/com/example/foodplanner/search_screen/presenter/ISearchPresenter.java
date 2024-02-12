package com.example.foodplanner.search_screen.presenter;

import com.example.foodplanner.model.dto.SearchItem;

import java.util.List;

public interface ISearchPresenter {
    void getCategoryList();
    void getIngredientList();
    void getCountryList();
    void getMealsList(String name, int type);
}
