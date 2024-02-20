package com.example.foodplanner.model.dto;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
@Entity(tableName = "inspiration_meal")
public class InspirationMeal {
    @PrimaryKey
    @NonNull
    public String id;
    @NonNull
    public String date;
    public String name;
    public String category;
    public String originCountry;
    public String instructions;
    public String imageUrl;
    public String tags;
    public String videoUrl;
    public ArrayList<MealIngredient> ingredients;

    public InspirationMeal(@NonNull String id, @NonNull String date, String name, String category, String originCountry, String instructions, String imageUrl, String tags, String videoUrl, ArrayList<MealIngredient> ingredients) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.category = category;
        this.originCountry = originCountry;
        this.instructions = instructions;
        this.imageUrl = imageUrl;
        this.tags = tags;
        this.videoUrl = videoUrl;
        this.ingredients = ingredients;
    }
    public InspirationMeal(Meal meal, String date){
        this.id = meal.getId();
        this.date = date;
        this.name = meal.getName();
        this.category = meal.getCategory();
        this.originCountry = meal.getOriginCountry();
        this.instructions = meal.getInstructions();
        this.imageUrl = meal.getImageUrl();
        this.tags = meal.getTags();
        this.videoUrl = meal.getVideoUrl();
        this.ingredients = meal.getIngredients();
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
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

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public ArrayList<MealIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<MealIngredient> ingredients) {
        this.ingredients = ingredients;
    }
}
