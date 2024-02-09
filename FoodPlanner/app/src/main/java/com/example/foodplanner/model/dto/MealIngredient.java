package com.example.foodplanner.model.dto;

public class MealIngredient {
    String name;
    String measure;

    String imageUrl;

    public MealIngredient(String name, String measure) {
        this.name = name;
        this.measure = measure;
        this.imageUrl = getImageUrl();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getImageUrl() {
        return "https://www.themealdb.com/images/ingredients/"+ name + ".png";
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
