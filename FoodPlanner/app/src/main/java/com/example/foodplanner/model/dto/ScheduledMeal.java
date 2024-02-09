package com.example.foodplanner.model.dto;

import java.util.ArrayList;

public class ScheduledMeal {
    public java.lang.String id;
    public java.lang.String name;
    public java.lang.String category;
    public java.lang.String originCountry;
    public java.lang.String istructions;
    public java.lang.String imageUrl;
    public java.lang.String tags;
    public java.lang.String videoUrl;
    ArrayList<MealIngridient> ingridients;
    String dayOfWeek;

    public ScheduledMeal(java.lang.String id, java.lang.String name, java.lang.String category, java.lang.String originCountry, java.lang.String istructions, java.lang.String imageUrl, java.lang.String tags, java.lang.String videoUrl, ArrayList<MealIngridient> ingridients, String dayOfWeek) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.originCountry = originCountry;
        this.istructions = istructions;
        this.imageUrl = imageUrl;
        this.tags = tags;
        this.videoUrl = videoUrl;
        this.ingridients = ingridients;
        this.dayOfWeek = dayOfWeek;
    }

    public java.lang.String getId() {
        return id;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }

    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public java.lang.String getCategory() {
        return category;
    }

    public void setCategory(java.lang.String category) {
        this.category = category;
    }

    public java.lang.String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(java.lang.String originCountry) {
        this.originCountry = originCountry;
    }

    public java.lang.String getIstructions() {
        return istructions;
    }

    public void setIstructions(java.lang.String istructions) {
        this.istructions = istructions;
    }

    public java.lang.String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(java.lang.String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public java.lang.String getTags() {
        return tags;
    }

    public void setTags(java.lang.String tags) {
        this.tags = tags;
    }

    public java.lang.String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(java.lang.String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public ArrayList<MealIngridient> getIngridients() {
        return ingridients;
    }

    public void setIngridients(ArrayList<MealIngridient> ingridients) {
        this.ingridients = ingridients;
    }
}
