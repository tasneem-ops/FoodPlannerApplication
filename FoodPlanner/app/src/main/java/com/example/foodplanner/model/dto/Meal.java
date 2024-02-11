package com.example.foodplanner.model.dto;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.lang.reflect.Field;
import java.util.ArrayList;

@Entity(tableName = "favorite_meals")
public class Meal {
    @PrimaryKey
    @NonNull
    public String id;
    public String name;
    public String category;
    public String originCountry;
    public String instructions;
    public String imageUrl;
    public String tags;
    public String videoUrl;
    public ArrayList<MealIngredient> ingredients;

    public Meal(String id, String name, String category, String originCountry, String instructions, String imageUrl, String tags, String videoUrl, ArrayList<MealIngredient> ingredients) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.originCountry = originCountry;
        this.instructions = instructions;
        this.imageUrl = imageUrl;
        this.tags = tags;
        this.videoUrl = videoUrl;
        this.ingredients = ingredients;
    }

    public Meal(ApiMeal meal){
        setId(meal.getId());
        setName(meal.getName());
        setCategory(meal.getCategory());
        setImageUrl(meal.getImageUrl());
        setTags(meal.getTags());
        setVideoUrl(meal.getVideoUrl());
        setOriginCountry(meal.getArea());
        setInstructions(meal.getInstructions());
        ArrayList<MealIngredient> ingredientsList = new ArrayList<>();
        try {
            Class<?> reflectionClass = getClass();
            for (int i = 1; i <= 20; i++) {
                Field ingredientField = reflectionClass.getDeclaredField("strIngredient" + i);
                Field measureField = reflectionClass.getDeclaredField("strMeasure" + i);
                ingredientField.setAccessible(true);
                measureField.setAccessible(true);
                String ingredient = (String) ingredientField.get(this);
                String measure = (String) measureField.get(this);
                if (ingredient != null && !ingredient.isEmpty()) {
                    ingredientsList.add(new MealIngredient(ingredient, measure));
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        setIngredients(ingredientsList);
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
