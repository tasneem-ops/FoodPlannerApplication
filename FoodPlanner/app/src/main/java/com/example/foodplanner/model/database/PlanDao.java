package com.example.foodplanner.model.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplanner.model.dto.PlanMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface PlanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(PlanMeal meal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAllMeals(PlanMeal... meals);

    @Query("SELECT * FROM plan_meals WHERE userID= :userID")
    Flowable<List<PlanMeal>> getAllPlanMeals(String userID);

    @Query("SELECT * FROM plan_meals WHERE id= :id AND userID= :userID")
    Flowable<PlanMeal> getMealById(String id, String userID);

    @Query("SELECT * FROM plan_meals WHERE date= :day AND userID= :userID")
    Flowable<List<PlanMeal>> getMealByDay(String day, String userID);

    @Delete
    Completable deleteMeal(PlanMeal meal);
}
