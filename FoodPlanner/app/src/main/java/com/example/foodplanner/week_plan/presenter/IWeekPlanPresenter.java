package com.example.foodplanner.week_plan.presenter;

public interface IWeekPlanPresenter {
    void getTodayList();

    void getTomorrowList();

    void getAnyDayList(String date);
}
