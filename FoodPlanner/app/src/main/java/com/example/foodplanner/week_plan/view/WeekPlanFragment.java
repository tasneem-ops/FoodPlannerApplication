package com.example.foodplanner.week_plan.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.authentication.view.AuthenticationActivity;
import com.example.foodplanner.model.database.LocalDataSource;
import com.example.foodplanner.model.dto.PlanMeal;
import com.example.foodplanner.model.network.APIRemoteDataSource;
import com.example.foodplanner.model.repository.Repository;
import com.example.foodplanner.week_plan.presenter.IWeekPlanPresenter;
import com.example.foodplanner.week_plan.presenter.WeekPlanPresenter;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WeekPlanFragment extends Fragment implements IViewWeekPlan, OnPlanMealClickListener{
    RecyclerView todayRecyclerView, tomorrowRecyclerView, anyDayRecyclerView;
    TextView picDate;
    LinearLayoutManager todayLayoutManager, tomorrowLayoutManager, anyDayLayoutManager;
    PlanMealListAdapter todayAdapter, tomorrowAdapter, anyDayAdapter;
    ConstraintLayout notLoggedInLayout;
    Group group;
    Button loginButton;
    Context context;
    IWeekPlanPresenter presenter;
    String selectedDate = "";
    Disposable todayDisposable, tomorrowDisposable, anyDateDisposable;
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
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            showNotLoggedIn();
        }
        else {
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
        group = view.findViewById(R.id.group_plan);
        notLoggedInLayout = view.findViewById(R.id.not_loggedin_plan);
        loginButton = view.findViewById(R.id.login_plan);
    }
    private void showNotLoggedIn() {
        group.setVisibility(View.GONE);
        notLoggedInLayout.setVisibility(View.VISIBLE);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });
    }
    @Override
    public void onPlanMealClicked(PlanMeal planMeal) {
        WeekPlanFragmentDirections.ActionWeekPlanFragmentToDetailFragment action = WeekPlanFragmentDirections.actionWeekPlanFragmentToDetailFragment(planMeal.getId());
        Navigation.findNavController(picDate).navigate(action);
    }

    @Override
    public void setTodayList(Flowable<List<PlanMeal>> meals) {
        todayDisposable = meals.observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    todayAdapter.setList(list);
                    todayAdapter.notifyDataSetChanged();
                });
    }

    @Override
    public void setTomorrowList(Flowable<List<PlanMeal>> meals) {
        tomorrowDisposable = meals.observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    tomorrowAdapter.setList(list);
                    tomorrowAdapter.notifyDataSetChanged();
                });
    }

    @Override
    public void setAnyDayList(Flowable<List<PlanMeal>> meals, String date) {
        if(date.equals(selectedDate)){
            anyDateDisposable = meals.observeOn(AndroidSchedulers.mainThread())
                    .subscribe(list -> {
                        anyDayAdapter.setList(list);
                        anyDayAdapter.notifyDataSetChanged();
                    });
        }
    }
    private void showDatePicker(){
        MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(limitRange(MaterialDatePicker.todayInUtcMilliseconds()))
                .build();
        datePicker.show(getChildFragmentManager(), "tag");
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.setTimeInMillis((Long) selection);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                selectedDate = dateFormat.format(calendar.getTime());
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
    private CalendarConstraints limitRange(long today) {
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setValidator(DateValidatorPointForward.from(today));
        return constraintsBuilder.build();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(todayDisposable !=null && !todayDisposable.isDisposed()){
            todayDisposable.dispose();
        }
        if(tomorrowDisposable !=null && !tomorrowDisposable.isDisposed()){
            tomorrowDisposable.dispose();
        }
        if(anyDateDisposable !=null && !anyDateDisposable.isDisposed()){
            anyDateDisposable.dispose();
        }
    }
}