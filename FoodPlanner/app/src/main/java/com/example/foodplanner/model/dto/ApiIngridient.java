package com.example.foodplanner.model.dto;

import com.google.gson.annotations.SerializedName;

public class ApiIngridient {
    @SerializedName("idIngredient")
    public String id;
    @SerializedName("strIngredient")
    public String name;
    @SerializedName("strDescription")
    public String description;
    @SerializedName("strType")
    public Object strType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getStrType() {
        return strType;
    }

    public void setStrType(Object strType) {
        this.strType = strType;
    }
}
