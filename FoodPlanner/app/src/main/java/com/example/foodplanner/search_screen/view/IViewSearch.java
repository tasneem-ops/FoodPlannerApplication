package com.example.foodplanner.search_screen.view;

import com.example.foodplanner.model.dto.SearchItem;
import java.util.List;

public interface IViewSearch {
    void setMealList(List<SearchItem> list);
    void setCategoryList(List<SearchItem> list);
    void setCountryList(List<SearchItem> list);
    void setIngredientList(List<SearchItem> list);
    void showError(String errorMessage);
}
