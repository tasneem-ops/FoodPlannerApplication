package com.example.foodplanner.model.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplanner.model.dto.InspirationMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface InspirationMealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(InspirationMeal meal);
    @Query("SELECT * FROM inspiration_meal")
    Flowable<List<InspirationMeal>> getMeal();
    @Query("DELETE FROM inspiration_meal")
    Completable deleteAll();
}
