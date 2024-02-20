package com.example.foodplanner.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiIngridientList {
    @SerializedName("meals")
    public List<ApiIngridient> ingridientList;

    public List<ApiIngridient> getIngridientList() {
        return ingridientList;
    }

    public void setIngridientList(List<ApiIngridient> ingridientList) {
        this.ingridientList = ingridientList;
    }
}
