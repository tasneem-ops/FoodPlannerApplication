package com.example.foodplanner.week_plan.view;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.dto.PlanMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface IViewWeekPlan {
    void setTodayList(Flowable<List<PlanMeal>> meals);
    void setTomorrowList(Flowable<List<PlanMeal>> meals);
    void setAnyDayList(Flowable<List<PlanMeal>> meals, String date);
}
