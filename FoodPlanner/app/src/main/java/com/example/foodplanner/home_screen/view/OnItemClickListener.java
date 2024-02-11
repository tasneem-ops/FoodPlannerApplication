package com.example.foodplanner.home_screen.view;

import com.example.foodplanner.model.dto.Area;
import com.example.foodplanner.model.dto.Category;

public interface OnItemClickListener {
    void onCategoryClicked(Category category);
    void onAreaClicked(Area area);
}