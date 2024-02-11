package com.example.foodplanner.model.dto;

import com.google.gson.annotations.SerializedName;

public class Area {
    @SerializedName("strArea")
    String name;

    public Area(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnailUrl(){
        return AreaMealMap.map.get(name);
    }
}
