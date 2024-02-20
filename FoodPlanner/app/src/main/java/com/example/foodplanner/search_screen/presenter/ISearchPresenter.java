package com.example.foodplanner.search_screen.presenter;

import com.example.foodplanner.model.dto.SearchItem;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;

public interface ISearchPresenter {
    @NonNull Single<List<SearchItem>> getCategoryList();
    @NonNull Single<List<SearchItem>> getIngredientList();
    @NonNull Single<List<SearchItem>> getCountryList();
    @NonNull Single<List<SearchItem>> getMealsList(String name, int type);
}
