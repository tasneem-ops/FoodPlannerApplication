package com.example.foodplanner.search_screen.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Fade;
import androidx.transition.Scene;
import androidx.transition.TransitionManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.model.database.LocalDataSource;
import com.example.foodplanner.model.dto.SearchItem;
import com.example.foodplanner.model.network.APIRemoteDataSource;
import com.example.foodplanner.model.repository.Repository;
import com.example.foodplanner.search_screen.presenter.ISearchPresenter;
import com.example.foodplanner.search_screen.presenter.SearchPresenter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

public class SearchFragment extends Fragment implements IViewSearch, OnSearchClickListener {

    RecyclerView searchRecyclerView;
    GridLayoutManager layoutManager;
    TextInputEditText textInputEditText;
    TextInputLayout textInputLayout;
    TextView filterByText;
    SearchResultGridAdapter searchResultGridAdapter;
    List<SearchItem> categoryList, areaList, ingredientList;
    ISearchPresenter presenter;
    int type = 0;
    Chip categoryChip, areaChip, ingredientChip;
    ChipGroup chipGroup;
    Disposable categoryDisposable, areaDisposable, ingredientDisposable, mealDisposable, searchDisposable;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        initRecyclerView();
        presenter = new SearchPresenter(this, Repository.getInstance(LocalDataSource.getInstance(getContext()), APIRemoteDataSource.getInstance()));

        String name = SearchFragmentArgs.fromBundle(getArguments()).getName();
        int typeArgs = SearchFragmentArgs.fromBundle(getArguments()).getType();
        if(name != null && !(name.equals("")) && typeArgs != -1){
            type = typeArgs;
            getMeals(name);
        }
        else{
            categoryDisposable = presenter.getCategoryList().observeOn(AndroidSchedulers.mainThread())
                    .subscribe(categoryList1 -> {
                        setCategoryList(categoryList1);
                    }, error-> showError(error.getMessage()));
            areaDisposable = presenter.getCountryList().observeOn(AndroidSchedulers.mainThread())
                    .subscribe(areaList1 -> {
                        setCountryList(areaList1);
                    }, error-> showError(error.getMessage()));
            ingredientDisposable = presenter.getIngredientList().observeOn(AndroidSchedulers.mainThread())
                    .subscribe(ingredientList1->{
                        setIngredientList(ingredientList1);
                    }, error-> showError(error.getMessage()));
        }

    }


    private void initUI(View view) {
        searchRecyclerView = view.findViewById(R.id.searchRecyclerView);
        textInputEditText = view.findViewById(R.id.inputEditText);
        textInputLayout = view.findViewById(R.id.search_bar);
        filterByText = view.findViewById(R.id.filterByText);
        chipGroup = view.findViewById(R.id.chipGroup);
        categoryChip = view.findViewById(R.id.categoryChip);
        areaChip = view.findViewById(R.id.areaChip);
        ingredientChip = view.findViewById(R.id.ingredientChip);
        categoryChip.setChecked(true);
        type = Types.CATEGORY;
        categoryChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    updateList(categoryList);
                    textInputLayout.setHint(R.string.search_by_category);
                    textInputEditText.setText("");
                    type = Types.CATEGORY;
                }
            }
        });
        areaChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    updateList(areaList);
                    textInputLayout.setHint(R.string.search_by_country);
                    textInputEditText.setText("");
                    type = Types.COUNTRY;
                }
            }
        });
        ingredientChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    updateList(ingredientList);
                    textInputLayout.setHint(R.string.search_by_ingredient);
                    textInputEditText.setText("");
                    type = Types.INGREDIENT;
                }
            }
        });
    }
    private void initRecyclerView() {
        layoutManager = new GridLayoutManager(getContext(), 2);
        searchRecyclerView.setHasFixedSize(true);
        searchRecyclerView.setLayoutManager(layoutManager);
        searchResultGridAdapter = new SearchResultGridAdapter(getContext(), new ArrayList<>(), this);
        searchRecyclerView.setAdapter(searchResultGridAdapter);
    }
    private void updateList(List<SearchItem> list){
        if(list !=null && ! (list.equals(Collections.emptyList()))){
            searchResultGridAdapter.setList(list);
            searchResultGridAdapter.notifyDataSetChanged();
            Observable<SearchItem> listObservable = Observable.fromIterable(list);
            textInputEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    List<SearchItem> filteredItems = new ArrayList<>();
                    searchDisposable = listObservable.filter(searchItem -> (searchItem.getName().toLowerCase()).contains(charSequence.toString().toLowerCase()))
                            .subscribe(searchItem -> filteredItems.add(searchItem),
                                    error ->{},
                                    ()->{updateRecyclerView(filteredItems);});
                }
                @Override
                public void afterTextChanged(Editable editable) {}
            });
        }
        else{

        }
    }
    private void updateRecyclerView(List<SearchItem> filteredItems){
        if(filteredItems !=null && ! (filteredItems.equals(Collections.emptyList()))){
            searchResultGridAdapter.setList(filteredItems);
            searchResultGridAdapter.notifyDataSetChanged();
        }
        else{
            searchResultGridAdapter.setList(new ArrayList<>());
            searchResultGridAdapter.notifyDataSetChanged();
        }

    }
    @Override
    public void setMealList(List<SearchItem> list) {
        updateList(list);
    }

    @Override
    public void setCategoryList(List<SearchItem> list) {
        categoryList = list;
        updateList(list);
    }

    @Override
    public void setCountryList(List<SearchItem> list) {
        areaList = list;
    }

    @Override
    public void setIngredientList(List<SearchItem> list) {
        ingredientList = list;
        Log.i("TAG", "setIngredientList: " + list);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(SearchItem item) {
        if(type == Types.MEAL){
            SearchFragmentDirections.ActionSearchFragmentToDetailFragment action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(item.getId());
            Navigation.findNavController(filterByText).navigate(action);
        }
        else{
            getMeals(item.getName());
        }
    }
    private void getMeals(String name){
        mealDisposable = presenter.getMealsList(name, type).observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals ->{
                    setMealList(meals);
                }, error -> showError(error.getMessage()));
        textInputLayout.setHint(R.string.search_by_meal_name);
        textInputEditText.setText("");
        chipGroup.setVisibility(View.GONE);
        filterByText.setText(String.format(getResources().getString(R.string.your_viewing_search), name));
        type = Types.MEAL;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(categoryDisposable !=null && !categoryDisposable.isDisposed()){
            categoryDisposable.dispose();
        }
        if(areaDisposable !=null && !areaDisposable.isDisposed()){
            areaDisposable.dispose();
        }
        if(ingredientDisposable !=null && !ingredientDisposable.isDisposed()){
            ingredientDisposable.dispose();
        }
        if(mealDisposable !=null && !mealDisposable.isDisposed()){
            mealDisposable.dispose();
        }
        if(searchDisposable !=null && !searchDisposable.isDisposed()){
            searchDisposable.dispose();
        }
    }
}