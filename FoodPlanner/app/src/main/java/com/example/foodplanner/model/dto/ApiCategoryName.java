package com.example.foodplanner.model.dto;

import com.google.gson.annotations.SerializedName;

public class ApiCategoryName {
    @SerializedName("strCategory")
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
