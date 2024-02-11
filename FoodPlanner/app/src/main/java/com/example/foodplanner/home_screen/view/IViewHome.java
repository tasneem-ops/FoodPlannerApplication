package com.example.foodplanner.home_screen.view;

import com.example.foodplanner.model.dto.Area;
import com.example.foodplanner.model.dto.Category;
import com.example.foodplanner.model.dto.Meal;

import java.util.List;

public interface IViewHome {
    public void setMealOfTheDay(Meal meal);
    public void setCategoryList(List<Category> categoryList);
    public void setAreaList(List<Area> areaList);
    void showError(String errMessage);
}
