package com.example.foodplanner.profile.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class ProfileFragment extends Fragment implements IViewProfile{
    FirebaseUser user;
    Button syncButton, logoutButton;
    TextView profileImagText, profileNameText, profileEmailText;
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
                getActivity().finish();
            }
        });
    }

    private void initUI(View view) {
        syncButton = view.findViewById(R.id.syncButton);
        logoutButton = view.findViewById(R.id.logoutButton);
        profileImagText = view.findViewById(R.id.profileImagText);
        profileEmailText = view.findViewById(R.id.profileEmailTxt);
        profileNameText = view.findViewById(R.id.profileNameTxt);
        profileImagText.setText(""+user.getDisplayName().charAt(0));
        profileNameText.setText(user.getDisplayName());
        profileEmailText.setText(user.getEmail());
    }
    private void updateSharedPref(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(AuthConstants.AUTHENTICATION, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(AuthConstants.IS_LOGGED_IN, false);
        sharedPreferences.edit().putString(AuthConstants.GOOGLE_CREDENTIALS, null);
        sharedPreferences.edit().putString(AuthConstants.EMAIL, null);
        sharedPreferences.edit().putString(AuthConstants.PASSWORD, null);
        sharedPreferences.edit().putString(AuthConstants.AUTH_METHOD, null);
    }

    @Override
    public void syncSuccessful() {
        Toast.makeText(context, "Data Synced Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void syncFaliure() {
        Toast.makeText(context, "Error Updating Data", Toast.LENGTH_SHORT).show();
    }
}