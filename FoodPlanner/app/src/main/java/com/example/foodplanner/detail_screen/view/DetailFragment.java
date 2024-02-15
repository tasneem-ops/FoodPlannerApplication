package com.example.foodplanner.detail_screen.view;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class DetailFragment extends Fragment implements IViewDetail{
    TextView mealNameTxt, originCountryTxt, stepsTxt;
    ImageView mealImage, iconFav, iconSchedule;
    WebView webView;
    RecyclerView ingredientRecyclerView;
    IDetailPresenter presenter;
    GridLayoutManager layoutManager;
    IngredientGridAdapter ingridientGridAdapter;
    CardView addToFav, addToPlan;
    Meal currentMeal;
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
        addToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentMeal.setUserID(FirebaseAuth.getInstance().getCurrentUser().getUid());
                presenter.addMealToFav(currentMeal);
                iconFav.setImageResource(R.drawable.favorite_fill);
            }
        });
        addToPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentMeal.setUserID(FirebaseAuth.getInstance().getCurrentUser().getUid());
                MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();
                datePicker.show(getChildFragmentManager(), "tag");
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                        calendar.setTimeInMillis((Long) selection);
//                        calendar = Calendar.getInstance();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        String selectedDate = dateFormat.format(calendar.getTime());
                        Toast.makeText(getContext(), "Selected Date: "+ selectedDate, Toast.LENGTH_SHORT).show();
                        presenter.addMealToPlan(currentMeal, selectedDate);
                    }
                });
                datePicker.addOnNegativeButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(iconSchedule, "Please select a date to save meal to plan", Snackbar.LENGTH_SHORT).show();
                    }
                });
                datePicker.addOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Snackbar.make(iconSchedule, "Please select a date to save meal to plan", Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }



    private void initUI(View view) {
        mealNameTxt = view.findViewById(R.id.mealName);
        originCountryTxt = view.findViewById(R.id.txtOriginCountry);
        stepsTxt = view.findViewById(R.id.textSteps);
        mealImage = view.findViewById(R.id.mealImage);
        webView = view.findViewById(R.id.mealVideo);
        ingredientRecyclerView = view.findViewById(R.id.ingredientsRecyclerViewDetails);
        addToFav = view.findViewById(R.id.add_to_fav);
        addToPlan = view.findViewById(R.id.add_to_plan);
        iconFav = view.findViewById(R.id.icon_fav);
        iconSchedule = view.findViewById(R.id.icon_schedule);
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
        currentMeal = meal;
        mealNameTxt.setText(meal.getName());
        Glide.with(this).load(meal.getImageUrl())
                .apply(new RequestOptions().override(700,700))
                .placeholder(R.drawable.downloading)
                .error(R.drawable.image_error)
                .into(mealImage);
        originCountryTxt.setText(meal.getOriginCountry());
        stepsTxt.setText(meal.getInstructions());
        List<MealIngredient> ingredientList = meal.getIngredients();
        ingridientGridAdapter.setIngredientList(ingredientList);
        ingridientGridAdapter.notifyDataSetChanged();
        String videoUrl = meal.getVideoUrl();
        if(videoUrl !=null && ! videoUrl.equals("")){
            String video = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + videoUrl.substring(32) + "\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
            webView.loadData(video, "text/html", "utf-8");
            webView.setWebChromeClient(new WebChromeClient());
            webView.getSettings().setJavaScriptEnabled(true);
        }
    }

    @Override
    public void showError(String errMessage) {
        Toast.makeText(getContext(), errMessage , Toast.LENGTH_SHORT).show();
    }
}