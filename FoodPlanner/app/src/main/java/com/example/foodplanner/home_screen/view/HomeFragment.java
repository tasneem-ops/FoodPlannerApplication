package com.example.foodplanner.home_screen.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.R;
import com.example.foodplanner.home_screen.presenter.HomePresenter;
import com.example.foodplanner.home_screen.presenter.IHomePresenter;
import com.example.foodplanner.model.database.LocalDataSource;
import com.example.foodplanner.model.dto.Area;
import com.example.foodplanner.model.dto.Category;
import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.network.APIRemoteDataSource;
import com.example.foodplanner.model.repository.Repository;
import com.example.foodplanner.search_screen.view.Types;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;

public class HomeFragment extends Fragment implements IViewHome, OnItemClickListener{
    ImageView imageMealOfTheDay;
    TextView txtTitleMealOfTheDay;
    IHomePresenter presenter;
    RecyclerView categoryRecyclerView, areaRecyclerView;
    CategoryListAdapter categoryListAdapter;
    AreaListAdapter areaListAdapter;
    CardView cardMealOfTheDay;
    LinearLayoutManager categorylayoutManager, arealayoutManager;
    Context context;
    Disposable mealDisposable, categoryListDisposable, areaListDisposable;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        imageMealOfTheDay = view.findViewById(R.id.imageMealOfTheDay);
        txtTitleMealOfTheDay = view.findViewById(R.id.txtTitleMealOfTheDay);
        cardMealOfTheDay = view.findViewById(R.id.cardMealOfTheDay);
        initRecyclerView(view);
        presenter = new HomePresenter(this, Repository.getInstance(LocalDataSource.getInstance(getContext()),APIRemoteDataSource.getInstance()));
        boolean isConnected = checkInternetConnection();
        presenter.getMealOfTheDay(isConnected);
        categoryListDisposable = presenter.getCategoryList().observeOn(AndroidSchedulers.mainThread())
                .subscribe(categoryList -> {
                    setCategoryList(categoryList);
                }, error ->{showError(error.getMessage());});
        areaListDisposable = presenter.getAreaList().observeOn(AndroidSchedulers.mainThread())
                .subscribe(areaList -> {
                    setAreaList(areaList);
                }, error->{showError(error.getMessage());});
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mealDisposable != null && !mealDisposable.isDisposed()){
            mealDisposable.dispose();
        }
        if(categoryListDisposable != null && !categoryListDisposable.isDisposed()){
            categoryListDisposable.dispose();
        }
        if(areaListDisposable != null && !areaListDisposable.isDisposed()){
            areaListDisposable.dispose();
        }
        presenter.unregisterView();
    }

    private void initRecyclerView(View view) {
        categorylayoutManager = new LinearLayoutManager(getContext());
        categorylayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        arealayoutManager = new LinearLayoutManager(getContext());
        arealayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        categoryRecyclerView = view.findViewById(R.id.categoriesRecycler);
        categoryRecyclerView.setHasFixedSize(true);
        categoryRecyclerView.setLayoutManager(categorylayoutManager);
        categoryListAdapter = new CategoryListAdapter(getContext(), new ArrayList<>(), this);
        categoryRecyclerView.setAdapter(categoryListAdapter);
        areaRecyclerView = view.findViewById(R.id.areaRecycler);
        areaRecyclerView.setHasFixedSize(true);
        areaRecyclerView.setLayoutManager(arealayoutManager);
        areaListAdapter = new AreaListAdapter(getContext(), new ArrayList<>(), this);
        areaRecyclerView.setAdapter(areaListAdapter);
    }

    @Override
    public void setMealOfTheDay(Single<Meal> mealObservable) {
        mealDisposable = mealObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(meal ->{
                    updateMealOfTheDayUI(meal);
                }, error->{showError(error.getMessage());});
    }
    private void updateMealOfTheDayUI(Meal meal){
        Glide.with(context.getApplicationContext()).load(meal.getImageUrl())
                .apply(new RequestOptions().override(700,700))
                .placeholder(R.drawable.downloading)
                .error(R.drawable.image_error)
                .into(imageMealOfTheDay);
        txtTitleMealOfTheDay.setText(meal.getName());
        cardMealOfTheDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDetailScreen(view, meal.getId());
            }
        });
    }

    private void goToDetailScreen(View view, String id) {
        HomeFragmentDirections.ActionHomeFragmentToDetailFragment action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(id);
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void setCategoryList(List<Category> categoryList) {
        if(categoryList != null){
            categoryListAdapter.setList(categoryList);
            categoryListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setAreaList(List<Area> areaList) {
        if(areaList != null){
            areaListAdapter.setList(areaList);
            areaListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showError(String errMessage) {
        Toast.makeText(context,R.string.no_network, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryClicked(Category category) {
        HomeFragmentDirections.ActionHomeFragmentToSearchFragment action = HomeFragmentDirections.actionHomeFragmentToSearchFragment().setName(category.getCategoryName()).setType(Types.CATEGORY);
        Navigation.findNavController(imageMealOfTheDay).navigate(action);
    }

    @Override
    public void onAreaClicked(Area area) {
        HomeFragmentDirections.ActionHomeFragmentToSearchFragment action = HomeFragmentDirections.actionHomeFragmentToSearchFragment().setName(area.getName()).setType(Types.COUNTRY);
        Navigation.findNavController(imageMealOfTheDay).navigate(action);
    }

    private boolean checkInternetConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null){
            return false;
        }
        return networkInfo.isConnected();
    }
}