package com.example.foodplanner.model.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplanner.model.dto.Meal;
import java.util.List;

@Dao
public interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(Meal meal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllMeals(Meal... meals);

    @Query("SELECT * FROM favorite_meals")
    LiveData<List<Meal>> getAllFavorites();

    @Query("SELECT * FROM favorite_meals WHERE id= :id")
    LiveData<Meal> getMealById(String id);

    @Delete
    void deleteMeal(Meal meal);


}
