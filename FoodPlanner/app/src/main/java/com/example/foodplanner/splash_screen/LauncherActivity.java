package com.example.foodplanner.splash_screen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.foodplanner.R;

public class LauncherActivity extends AppCompatActivity {
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_launcher);
        NavigationUI.setupActionBarWithNavController(this, navController);
        getSupportActionBar().hide();
    }
}