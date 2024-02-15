package com.example.foodplanner.profile.presenter;

import androidx.annotation.NonNull;
import com.example.foodplanner.model.repository.Repository;
import com.example.foodplanner.profile.view.IViewProfile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfilePresenter implements IProfilePresenter {
    IViewProfile view;
    Repository repository;

    public ProfilePresenter(IViewProfile view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void syncData(){
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        repository.getAllFavorite(userID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealList -> {
                    Map<String, Object> userData = new HashMap<>();
                    Gson gson = new Gson();
                    userData.put("Favorites", gson.toJson(mealList));
                    updateFirebaseDatabase(userData);
                });
        repository.getAllPlanMeals(userID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealList -> {
                    Map<String, Object> userData = new HashMap<>();
                    Gson gson = new Gson();
                    userData.put("Plan", gson.toJson(mealList));
                    updateFirebaseDatabase(userData);
                });
    }

    private void updateFirebaseDatabase(Map<String, Object> userData){
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(userID)
            .update(userData)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    view.syncSuccessful();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    view.syncFaliure();
                }
            });
    }
}
