package com.example.foodplanner.week_plan.view;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.dto.PlanMeal;

import java.util.List;

public interface IViewWeekPlan {
    void setTodayList(LiveData<List<PlanMeal>> meals);
    void setTomorrowList(LiveData<List<PlanMeal>> meals);
    void setAnyDayList(LiveData<List<PlanMeal>> meals, String date);
}
