package com.example.foodplanner.model.dto;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Field;
import java.util.ArrayList;

@Entity(tableName = "favorite_meals", primaryKeys = {"id", "userID"})
public class Meal {
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
//        try {
//            Class<?> reflectionClass = getClass();
//            for (int i = 1; i <= 20; i++) {
//                Field ingredientField = reflectionClass.getDeclaredField("strIngredient" + i);
//                Field measureField = reflectionClass.getDeclaredField("strMeasure" + i);
//                ingredientField.setAccessible(true);
//                measureField.setAccessible(true);
//                String ingredient = (String) ingredientField.get(this);
//                String measure = (String) measureField.get(this);
//                if (ingredient != null && !ingredient.isEmpty()) {
//                    ingredientsList.add(new MealIngredient(ingredient, measure));
//                }
//            }
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
        addIngredientToList(meal.getStrIngredient1(), meal.getStrMeasure1(), ingredientsList);
        addIngredientToList(meal.getStrIngredient2(), meal.getStrMeasure2(), ingredientsList);
        addIngredientToList(meal.getStrIngredient3(), meal.getStrMeasure3(), ingredientsList);
        addIngredientToList(meal.getStrIngredient4(), meal.getStrMeasure4(), ingredientsList);
        addIngredientToList(meal.getStrIngredient5(), meal.getStrMeasure5(), ingredientsList);
        addIngredientToList(meal.getStrIngredient6(), meal.getStrMeasure6(), ingredientsList);
        addIngredientToList(meal.getStrIngredient7(), meal.getStrMeasure7(), ingredientsList);
        addIngredientToList(meal.getStrIngredient8(), meal.getStrMeasure8(), ingredientsList);
        addIngredientToList(meal.getStrIngredient9(), meal.getStrMeasure9(), ingredientsList);
        addIngredientToList(meal.getStrIngredient10(), meal.getStrMeasure10(), ingredientsList);
        addIngredientToList(meal.getStrIngredient11(), meal.getStrMeasure11(), ingredientsList);
        addIngredientToList(meal.getStrIngredient12(), meal.getStrMeasure12(), ingredientsList);
        addIngredientToList(meal.getStrIngredient13(), meal.getStrMeasure13(), ingredientsList);
        addIngredientToList(meal.getStrIngredient14(), meal.getStrMeasure14(), ingredientsList);
        addIngredientToList(meal.getStrIngredient15(), meal.getStrMeasure15(), ingredientsList);
        addIngredientToList(meal.getStrIngredient16(), meal.getStrMeasure16(), ingredientsList);
        addIngredientToList(meal.getStrIngredient17(), meal.getStrMeasure17(), ingredientsList);
        addIngredientToList(meal.getStrIngredient18(), meal.getStrMeasure18(), ingredientsList);
        addIngredientToList(meal.getStrIngredient19(), meal.getStrMeasure19(), ingredientsList);
        addIngredientToList(meal.getStrIngredient20(), meal.getStrMeasure20(), ingredientsList);
        setIngredients(ingredientsList);
    }
    private void addIngredientToList(String ingredient, String measure, ArrayList<MealIngredient> mealIngredients){
        if(ingredient!= null && !(ingredient.equals("")))
            mealIngredients.add(new MealIngredient(ingredient, measure));
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
