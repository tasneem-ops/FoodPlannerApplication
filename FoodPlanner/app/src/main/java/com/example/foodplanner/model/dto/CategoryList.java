package com.example.foodplanner.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryList {

    @SerializedName("categories")
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

}