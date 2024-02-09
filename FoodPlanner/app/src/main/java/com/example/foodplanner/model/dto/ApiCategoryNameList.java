package com.example.foodplanner.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiCategoryNameList {
    @SerializedName("meals")
    public List<Area> categories;
}
