package com.example.foodplanner.authentication.presenter;

import androidx.annotation.NonNull;

import com.example.foodplanner.model.database.LocalDataSource;
import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.dto.PlanMeal;
import com.example.foodplanner.model.network.APIRemoteDataSource;
import com.example.foodplanner.model.repository.Repository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class AuthPresenter implements IAuthPresenter {
    Repository repository;

    public AuthPresenter(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void loadDataFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Type listType = new TypeToken<List<Meal>>() {}.getType();
                        Type listPlanType = new TypeToken<List<PlanMeal>>() {}.getType();
                        Gson gson = new Gson();
                        List<Meal> favMeals = gson.fromJson(document.getString("Favorites"), listType);
                        List<Meal> planMeals = gson.fromJson(document.getString("Plan"), listPlanType);
                        if(favMeals != null)
                            repository.insertAllMealsToFav(favMeals.toArray(new Meal[0]));
                        if(planMeals != null)
                            repository.insertAllMealsToPlan(planMeals.toArray(new PlanMeal[0]));
                    } else {
                    }
                } else {
                }
            }
        });
    }
}
