package com.example.foodplanner.week_plan.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.model.database.LocalDataSource;
import com.example.foodplanner.model.dto.PlanMeal;
import com.example.foodplanner.model.network.APIRemoteDataSource;
import com.example.foodplanner.model.repository.Repository;
import com.example.foodplanner.week_plan.presenter.IWeekPlanPresenter;
import com.example.foodplanner.week_plan.presenter.WeekPlanPresenter;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class WeekPlanFragment extends Fragment implements IViewWeekPlan, OnPlanMealClickListener{
    RecyclerView todayRecyclerView, tomorrowRecyclerView, anyDayRecyclerView;
    TextView picDate;
    LinearLayoutManager todayLayoutManager, tomorrowLayoutManager, anyDayLayoutManager;
    PlanMealListAdapter todayAdapter, tomorrowAdapter, anyDayAdapter;
    Context context;
    IWeekPlanPresenter presenter;
    String selectedDate = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_week_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        initUI(view);
        initRecyclerView();
        presenter = new WeekPlanPresenter(this, Repository.getInstance(LocalDataSource.getInstance(context), APIRemoteDataSource.getInstance()));
        presenter.getTodayList();
        presenter.getTomorrowList();
        picDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
    }

    private void initRecyclerView() {
        todayLayoutManager = new LinearLayoutManager(context);
        todayLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        todayRecyclerView.setHasFixedSize(true);
        todayRecyclerView.setLayoutManager(todayLayoutManager);
        todayAdapter = new PlanMealListAdapter(context, new ArrayList<>(), this);
        todayRecyclerView.setAdapter(todayAdapter);

        tomorrowLayoutManager = new LinearLayoutManager(context);
        tomorrowLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        tomorrowRecyclerView.setHasFixedSize(true);
        tomorrowRecyclerView.setLayoutManager(tomorrowLayoutManager);
        tomorrowAdapter = new PlanMealListAdapter(context, new ArrayList<>(), this);
        tomorrowRecyclerView.setAdapter(tomorrowAdapter);

        anyDayLayoutManager = new LinearLayoutManager(context);
        anyDayLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        anyDayRecyclerView.setHasFixedSize(true);
        anyDayRecyclerView.setLayoutManager(anyDayLayoutManager);
        anyDayAdapter = new PlanMealListAdapter(context, new ArrayList<>(), this);
        anyDayRecyclerView.setAdapter(anyDayAdapter);
    }

    private void initUI(View view) {
        todayRecyclerView = view.findViewById(R.id.todayRecyclerView);
        tomorrowRecyclerView = view.findViewById(R.id.tomorrowRecyclerView);
        anyDayRecyclerView = view.findViewById(R.id.anyDayRecyclerView);
        picDate = view.findViewById(R.id.pic_date);
    }

    @Override
    public void onPlanMealClicked(PlanMeal planMeal) {
        WeekPlanFragmentDirections.ActionWeekPlanFragmentToDetailFragment action = WeekPlanFragmentDirections.actionWeekPlanFragmentToDetailFragment(planMeal.getId());
        Navigation.findNavController(picDate).navigate(action);
    }

    @Override
    public void setTodayList(LiveData<List<PlanMeal>> meals) {
        meals.observe(this, new Observer<List<PlanMeal>>() {
            @Override
            public void onChanged(List<PlanMeal> planMeals) {
                todayAdapter.setList(planMeals);
                todayAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void setTomorrowList(LiveData<List<PlanMeal>> meals) {
        meals.observe(this, new Observer<List<PlanMeal>>() {
            @Override
            public void onChanged(List<PlanMeal> planMeals) {
                tomorrowAdapter.setList(planMeals);
                tomorrowAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void setAnyDayList(LiveData<List<PlanMeal>> meals, String date) {
        if(date.equals(selectedDate)){
            meals.observe(this, new Observer<List<PlanMeal>>() {
                @Override
                public void onChanged(List<PlanMeal> planMeals) {
                    anyDayAdapter.setList(planMeals);
                    anyDayAdapter.notifyDataSetChanged();
                }
            });
        }
    }
    private void showDatePicker(){
        MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();
        datePicker.show(getChildFragmentManager(), "tag");
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.setTimeInMillis((Long) selection);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                selectedDate = dateFormat.format(calendar.getTime());
                Toast.makeText(getContext(), "Selected Date: "+ selectedDate, Toast.LENGTH_SHORT).show();
                presenter.getAnyDayList(selectedDate);
            }
        });
        datePicker.addOnNegativeButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Please select a date to show the plan", Snackbar.LENGTH_SHORT).show();
            }
        });
        datePicker.addOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Snackbar.make(picDate, "Please select a date to show the plan", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}