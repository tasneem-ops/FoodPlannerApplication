package com.example.foodplanner.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreaList {
    @SerializedName("meals")
    public List<Area> areas;
}
