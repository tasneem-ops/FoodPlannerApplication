package com.example.foodplanner.detail_screen.view;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;


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
    Context context;
    FirebaseUser firebaseUser;
    Disposable observableDetails;
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
        context = getContext();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        initUI(view);
        initRecyclerView();
        presenter = new DetailPresenter(this, Repository.getInstance(LocalDataSource.getInstance(getContext()), APIRemoteDataSource.getInstance()));
        String id = DetailFragmentArgs.fromBundle(getArguments()).getMealID();
        boolean isConnected = checkInternetConnection();
        presenter.getMealDetails(id, isConnected);
        addToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentMeal == null)
                    return;
                if(firebaseUser !=null){
                    currentMeal.setUserID(firebaseUser.getUid());
                    presenter.addMealToFav(currentMeal)
                            .observeOn(AndroidSchedulers.mainThread()).subscribe();
                    iconFav.setImageResource(R.drawable.favorite_fill);
                }
            }
        });
        addToPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentMeal == null)
                    return;
                if(firebaseUser == null)
                    return;
                currentMeal.setUserID(firebaseUser.getUid());
                MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText(R.string.select_date)
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .setCalendarConstraints(limitRange(MaterialDatePicker.todayInUtcMilliseconds()))
                        .build();
                datePicker.show(getChildFragmentManager(), "tag");

                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                        calendar.setTimeInMillis((Long) selection);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        String selectedDate = dateFormat.format(calendar.getTime());
                        presenter.addMealToPlan(currentMeal, selectedDate)
                                .observeOn(AndroidSchedulers.mainThread()).subscribe();
                    }
                });
                datePicker.addOnNegativeButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, R.string.please_select_date, Snackbar.LENGTH_SHORT).show();
                    }
                });
                datePicker.addOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Snackbar.make(view, R.string.please_select_date, Snackbar.LENGTH_SHORT).show();
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
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            addToFav.setVisibility(View.GONE);
            addToPlan.setVisibility(View.GONE);
        }
    }
    private void initRecyclerView() {
        layoutManager = new GridLayoutManager(getContext(), 3);
        ingredientRecyclerView.setHasFixedSize(true);
        ingredientRecyclerView.setLayoutManager(layoutManager);
        ingridientGridAdapter = new IngredientGridAdapter(getContext(), new ArrayList<>());
        ingredientRecyclerView.setAdapter(ingridientGridAdapter);
    }

    @Override
    public void setMeal(Single<Meal> observableMeal) {
        observableDetails = observableMeal.observeOn(AndroidSchedulers.mainThread())
                .subscribe(meal -> {
                    updateMealUI(meal);
                }, error ->{showError(error.getMessage());});
    }
    void updateMealUI(Meal meal){
        currentMeal = meal;
        mealNameTxt.setText(meal.getName());
        Glide.with(context).load(meal.getImageUrl())
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
        presenter.isFav(meal.getId());
    }

    @Override
    public void showError(String errMessage) {
        Toast.makeText(context, R.string.no_network , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setFav() {
        iconFav.setImageResource(R.drawable.favorite_fill);
        iconFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentMeal == null)
                    return;
                if(firebaseUser !=null){
                    currentMeal.setUserID(firebaseUser.getUid());
                    presenter.deleteMealFromFav(currentMeal)
                            .observeOn(AndroidSchedulers.mainThread()).subscribe();
                    iconFav.setImageResource(R.drawable.favorite_empty_white);
                }
            }
        });
    }

    private boolean checkInternetConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null){
            return false;
        }
        return networkInfo.isConnected();
    }
    private CalendarConstraints limitRange(long today) {
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setValidator(DateValidatorPointForward.from(today));
        return constraintsBuilder.build();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(observableDetails != null && !observableDetails.isDisposed()){
            observableDetails.dispose();
        }
        presenter.unregisterView();
    }
}