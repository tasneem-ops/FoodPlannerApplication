package com.example.foodplanner.favorites.view;

import android.content.Context;
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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.foodplanner.R;
import com.example.foodplanner.authentication.view.AuthenticationActivity;
import com.example.foodplanner.favorites.presenter.FavPresenter;
import com.example.foodplanner.favorites.presenter.IFavPresenter;
import com.example.foodplanner.home_screen.presenter.HomePresenter;
import com.example.foodplanner.home_screen.view.HomeFragmentDirections;
import com.example.foodplanner.model.database.LocalDataSource;
import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.network.APIRemoteDataSource;
import com.example.foodplanner.model.repository.Repository;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class FavoritesFragment extends Fragment implements IViewFav,OnFavClickListener{
    RecyclerView favoritesRecyclerView;
    LinearLayoutManager layoutManager;
    FavListAdapter favListAdapter;
    ConstraintLayout notLoggedInLayout;
    Button loginButton;
    Group group;
    Context context;
    IFavPresenter presenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        initUI(view);
        presenter = new FavPresenter(this, Repository.getInstance(LocalDataSource.getInstance(getContext()), APIRemoteDataSource.getInstance()));
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            showNotLoggedIn();
        }
        else{
            presenter.getFavMeals().observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mealList -> {
                        favListAdapter.setList(mealList);
                        favListAdapter.notifyDataSetChanged();
                    });
        }
    }
    private void initUI(View view){
        favoritesRecyclerView = view.findViewById(R.id.favoritesRecyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        favoritesRecyclerView.setHasFixedSize(true);
        favoritesRecyclerView.setLayoutManager(layoutManager);
        favListAdapter = new FavListAdapter(context, new ArrayList<>(), this);
        favoritesRecyclerView.setAdapter(favListAdapter);
        group = view.findViewById(R.id.group_fav);
        notLoggedInLayout = view.findViewById(R.id.not_loggedin_fav);
        loginButton = view.findViewById(R.id.login_fav);
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
    public void onMealClick(Meal meal) {
        FavoritesFragmentDirections.ActionFavoritesFragmentToDetailFragment action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailFragment(meal.getId());
        Navigation.findNavController(favoritesRecyclerView).navigate(action);
    }

    @Override
    public void onDeleteClick(Meal meal) {
        presenter.deleteFavMeal(meal).observeOn(AndroidSchedulers.mainThread()).subscribe();
    }

    @Override
    public void setFavList(LiveData<List<Meal>> meals) {
        meals.observe(this, new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                favListAdapter.setList(meals);
                favListAdapter.notifyDataSetChanged();
            }
        });
    }
}