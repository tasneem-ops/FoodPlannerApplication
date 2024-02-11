package com.example.foodplanner.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.dto.PlanMeal;

@Database(entities = {Meal.class, PlanMeal.class}, version = 1, exportSchema = false)
@TypeConverters({ArrayTypeConverter.class})
public abstract class FoodPlannerDatabase extends RoomDatabase {
    private static FoodPlannerDatabase instance = null;
    public abstract FavoritesDao getFavoritesDao();
    public abstract PlanDao getPlanDao();

    public static synchronized FoodPlannerDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context,
                    FoodPlannerDatabase.class, "foodPlannerDB").build();
        }
        return instance;
    }
}
