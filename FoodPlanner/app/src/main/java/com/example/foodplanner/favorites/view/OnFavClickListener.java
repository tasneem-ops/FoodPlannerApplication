package com.example.foodplanner.favorites.view;

import com.example.foodplanner.model.dto.Meal;

public interface OnFavClickListener {
    void onMealClick(Meal meal);
    void onDeleteClick(Meal meal);
}
