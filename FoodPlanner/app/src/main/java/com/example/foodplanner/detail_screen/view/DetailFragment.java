package com.example.foodplanner.detail_screen.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.R;
import com.example.foodplanner.detail_screen.presenter.DetailPresenter;
import com.example.foodplanner.detail_screen.presenter.IDetailPresenter;
import com.example.foodplanner.model.database.LocalDataSource;
import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.dto.MealIngredient;
import com.example.foodplanner.model.network.APIRemoteDataSource;
import com.example.foodplanner.model.repository.Repository;

import java.util.ArrayList;
import java.util.List;


public class DetailFragment extends Fragment implements IViewDetail{
    TextView mealNameTxt, originCountryTxt, stepsTxt;
    ImageView mealImage;
    WebView webView;
    RecyclerView ingredientRecyclerView;
    IDetailPresenter presenter;
    GridLayoutManager layoutManager;
    IngredientGridAdapter ingridientGridAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        initRecyclerView();
        presenter = new DetailPresenter(this, Repository.getInstance(LocalDataSource.getInstance(getContext()), APIRemoteDataSource.getInstance()));
        String id = DetailFragmentArgs.fromBundle(getArguments()).getMealID();
        presenter.getMealDetails(id);
    }



    private void initUI(View view) {
        mealNameTxt = view.findViewById(R.id.mealName);
        originCountryTxt = view.findViewById(R.id.txtOriginCountry);
        stepsTxt = view.findViewById(R.id.textSteps);
        mealImage = view.findViewById(R.id.mealImage);
        webView = view.findViewById(R.id.mealVideo);
        ingredientRecyclerView = view.findViewById(R.id.ingredientsRecyclerViewDetails);
    }
    private void initRecyclerView() {
        layoutManager = new GridLayoutManager(getContext(), 3);
        ingredientRecyclerView.setHasFixedSize(true);
        ingredientRecyclerView.setLayoutManager(layoutManager);
        ingridientGridAdapter = new IngredientGridAdapter(getContext(), new ArrayList<>());
        ingredientRecyclerView.setAdapter(ingridientGridAdapter);
    }

    @Override
    public void setMeal(Meal meal) {
        mealNameTxt.setText(meal.getName());
        Glide.with(this).load(meal.getImageUrl())
                .apply(new RequestOptions().override(700,700))
                .placeholder(R.drawable.downloading)
                .error(R.drawable.broken_image)
                .into(mealImage);
        originCountryTxt.setText(meal.getOriginCountry());
        stepsTxt.setText(meal.getInstructions());
        List<MealIngredient> ingredientList = meal.getIngredients();
        ingridientGridAdapter.setIngredientList(ingredientList);
        ingridientGridAdapter.notifyDataSetChanged();
        String videoUrl = meal.getVideoUrl();
        Toast.makeText(getContext(), videoUrl, Toast.LENGTH_LONG).show();
        String video = "<iframe width=\"100%\" height=\"100%\" src=\"" + videoUrl + "\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
//        String x  = "<iframe width=\"100%\" height=\"100%\" src=\"https://m.youtube.com/embed/V2KCAfHjySQ?si=BPXNnL-5InLcCeyS\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        webView.loadData(video, "text/html", "utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
    }

    @Override
    public void showError(String errMessage) {
        Toast.makeText(getContext(), errMessage , Toast.LENGTH_SHORT).show();
    }
}