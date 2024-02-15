package com.example.foodplanner.favorites.view;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.dto.Meal;

import java.util.List;

public interface IViewFav {
    void setFavList(LiveData<List<Meal>> meals);
}
