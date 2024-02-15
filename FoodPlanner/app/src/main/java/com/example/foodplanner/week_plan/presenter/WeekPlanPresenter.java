package com.example.foodplanner.week_plan.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.dto.PlanMeal;
import com.example.foodplanner.model.repository.Repository;
import com.example.foodplanner.week_plan.view.IViewWeekPlan;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class WeekPlanPresenter implements IWeekPlanPresenter {
    IViewWeekPlan view;
    Repository repository;
    String userID;

    public WeekPlanPresenter(IViewWeekPlan view, Repository repository) {
        this.view = view;
        this.repository = repository;
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public void getTodayList(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String date = dateFormat.format(calendar.getTime());
        view.setTodayList(repository.getPlanMealByDay(date, userID));
    }
    @Override
    public void getTomorrowList(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String date = dateFormat.format(calendar.getTime());
        view.setTomorrowList(repository.getPlanMealByDay(date, userID));
    }
    @Override
    public void getAnyDayList(String date){
        view.setAnyDayList(repository.getPlanMealByDay(date, userID), date);
    }
}
