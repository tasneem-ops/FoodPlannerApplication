package com.example.foodplanner.model.dto;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Meal {
    public String id;
    public String name;
    public String category;
    public String originCountry;
    public String istructions;
    public String imageUrl;
    public String tags;
    public String videoUrl;
    ArrayList<MealIngridient> ingridients;

    public Meal(String id, String name, String category, String originCountry, String nstructions, String imageUrl, String tags, String videoUrl, ArrayList<MealIngridient> ingridients) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.originCountry = originCountry;
        this.istructions = nstructions;
        this.imageUrl = imageUrl;
        this.tags = tags;
        this.videoUrl = videoUrl;
        this.ingridients = ingridients;
    }

    public Meal() {

    }

    public Meal getMeal(ApiMeal meal){
        Meal mappedMeal = new Meal();
        mappedMeal.setId(meal.getId());
        mappedMeal.setName(meal.getName());
        mappedMeal.setCategory(meal.getCategory());
        mappedMeal.setImageUrl(meal.getImageUrl());
        mappedMeal.setTags(meal.getTags());
        mappedMeal.setVideoUrl(meal.getVideoUrl());
        mappedMeal.setOriginCountry(meal.getArea());
        mappedMeal.setIstructions(meal.getInstructions());
        ArrayList<MealIngridient> ingredients = new ArrayList<>();
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
                    ingredients.add(new MealIngridient(ingredient, measure));
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        mappedMeal.setIngridients(ingridients);
        return mappedMeal;
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

    public String getIstructions() {
        return istructions;
    }

    public void setIstructions(String istructions) {
        this.istructions = istructions;
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

    public ArrayList<MealIngridient> getIngridients() {
        return ingridients;
    }

    public void setIngridients(ArrayList<MealIngridient> ingridients) {
        this.ingridients = ingridients;
    }
}
