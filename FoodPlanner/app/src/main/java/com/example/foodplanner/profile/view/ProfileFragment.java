package com.example.foodplanner.profile.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.authentication.presenter.AuthConstants;
import com.example.foodplanner.authentication.view.AuthenticationActivity;
import com.example.foodplanner.model.database.LocalDataSource;
import com.example.foodplanner.model.network.APIRemoteDataSource;
import com.example.foodplanner.model.repository.Repository;
import com.example.foodplanner.profile.presenter.IProfilePresenter;
import com.example.foodplanner.profile.presenter.ProfilePresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class ProfileFragment extends Fragment implements IViewProfile{
    FirebaseUser user;
    Button syncButton, logoutButton, loginButton;
    TextView profileImagText, profileNameText, profileEmailText;
    Group group;
    ConstraintLayout notLoggedInLayout;
    Context context;
    IProfilePresenter presenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        presenter = new ProfilePresenter(this, Repository.getInstance(LocalDataSource.getInstance(context), APIRemoteDataSource.getInstance()));
        user = FirebaseAuth.getInstance().getCurrentUser();
        initUI(view);
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            showNotLoggedIn();
        }
        else{
            showNormalScreen();
        }

    }

    private void showNormalScreen() {
        profileImagText.setText(user.getDisplayName().charAt(0) + "");
        profileNameText.setText(user.getDisplayName());
        profileEmailText.setText(user.getEmail());
        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.syncData();
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                updateSharedPref();
                Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });
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

    private void initUI(View view) {
        syncButton = view.findViewById(R.id.syncButton);
        logoutButton = view.findViewById(R.id.logoutButton);
        profileImagText = view.findViewById(R.id.profileImagText);
        profileEmailText = view.findViewById(R.id.profileEmailTxt);
        profileNameText = view.findViewById(R.id.profileNameTxt);
        group = view.findViewById(R.id.group);
        notLoggedInLayout = view.findViewById(R.id.not_loggedin_profile);
        loginButton = view.findViewById(R.id.login_profile);
    }
    private void updateSharedPref(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(AuthConstants.AUTHENTICATION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(AuthConstants.IS_LOGGED_IN, false);
        editor.putString(AuthConstants.GOOGLE_CREDENTIALS, null);
        editor.putString(AuthConstants.EMAIL, null);
        editor.putString(AuthConstants.PASSWORD, null);
        editor.putString(AuthConstants.AUTH_METHOD, null);
        editor.apply();
    }

    @Override
    public void syncSuccessful() {
        Toast.makeText(context, R.string.data_sync_success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void syncFaliure() {
        Toast.makeText(context, R.string.data_sync_error, Toast.LENGTH_SHORT).show();
    }
}