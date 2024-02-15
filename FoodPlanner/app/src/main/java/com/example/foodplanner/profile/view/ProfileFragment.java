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
import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.network.APIRemoteDataSource;
import com.example.foodplanner.model.repository.Repository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileFragment extends Fragment {
    String userID;
    FirebaseUser user;
    Button syncButton, logoutButton;
    TextView profileImagText, profileNameText, profileEmailText;
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
        user = FirebaseAuth.getInstance().getCurrentUser();
        initUI(view);
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Repository repository = Repository.getInstance(LocalDataSource.getInstance(getContext()), APIRemoteDataSource.getInstance());
                repository.getAllFavorite(userID).observe(getViewLifecycleOwner(), new Observer<List<Meal>>() {
                    @Override
                    public void onChanged(List<Meal> mealList) {
                        Map<String, String> userData = new HashMap<>();
                        Gson gson = new Gson();
                        userData.put("Favorites", gson.toJson(mealList));
                        updateFirebaseDatabase(userData);
                    }
                });
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(AuthConstants.AUTHENTICATION, Context.MODE_PRIVATE);
                sharedPreferences.edit().putBoolean(AuthConstants.IS_LOGGED_IN, false);
                sharedPreferences.edit().putString(AuthConstants.GOOGLE_CREDENTIALS, null);
                sharedPreferences.edit().putString(AuthConstants.EMAIL, null);
                sharedPreferences.edit().putString(AuthConstants.PASSWORD, null);
                sharedPreferences.edit().putString(AuthConstants.AUTH_METHOD, null);
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
    private void updateFirebaseDatabase(Map<String, String> userData){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(userID)
            .set(userData)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getContext(), "Data Synced Successfully", Toast.LENGTH_SHORT).show();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Error Updating Data", Toast.LENGTH_SHORT).show();
                }
            });
    }
}