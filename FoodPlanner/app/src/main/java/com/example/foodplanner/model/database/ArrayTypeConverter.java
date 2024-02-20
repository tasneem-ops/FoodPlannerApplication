package com.example.foodplanner.model.database;

import androidx.room.TypeConverter;

import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.dto.MealIngredient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ArrayTypeConverter {
        @TypeConverter
        public static ArrayList<MealIngredient> fromString(String value) {
            Type listType = new TypeToken<ArrayList<MealIngredient>>() {}.getType();
            return new Gson().fromJson(value, listType);
        }

        @TypeConverter
        public static String fromArrayList(ArrayList<MealIngredient> list) {
            Gson gson = new Gson();
            String json = gson.toJson(list);
            return json;
        }
}
