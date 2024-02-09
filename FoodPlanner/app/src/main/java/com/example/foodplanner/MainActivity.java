package com.example.foodplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.foodplanner.model.database.LocalDataSource;
import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.dto.PlanMeal;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}