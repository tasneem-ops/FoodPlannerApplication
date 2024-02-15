package com.example.foodplanner.model.dto;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.util.ArrayList;

@Entity(tableName = "plan_meals", primaryKeys = {"id", "userID"})
public class PlanMeal {
    @NonNull
    public String id;
    @NonNull
    public String userID;
    public String name;
    public String category;
    public String originCountry;
    public String instructions;
    public String imageUrl;
    public String tags;
    public String videoUrl;
    public ArrayList<MealIngredient> ingredients;
    public String date;

    public PlanMeal(String id, String name, String category, String originCountry, String instructions, String imageUrl, String tags, String videoUrl, ArrayList<MealIngredient> ingredients, String date) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.originCountry = originCountry;
        this.instructions = instructions;
        this.imageUrl = imageUrl;
        this.tags = tags;
        this.videoUrl = videoUrl;
        this.ingredients = ingredients;
        this.date = date;
    }

    public PlanMeal(Meal meal , String dayOfWeek){
        this.id = meal.getId();
        this.userID = meal.getUserID();
        this.name = meal.getName();
        this.category = meal.getCategory();
        this.originCountry = meal.getOriginCountry();
        this.instructions = meal.getInstructions();
        this.imageUrl = meal.imageUrl;
        this.tags = meal.getTags();
        this.videoUrl = meal.getVideoUrl();
        this.ingredients = meal.getIngredients();
        this.date = dayOfWeek;
    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(java.lang.String tags) {
        this.tags = tags;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(java.lang.String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public ArrayList<MealIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<MealIngredient> ingredients) {
        this.ingredients = ingredients;
    }
}
