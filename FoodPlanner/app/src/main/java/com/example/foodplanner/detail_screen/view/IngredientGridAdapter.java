package com.example.foodplanner.detail_screen.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.R;
import com.example.foodplanner.model.dto.MealIngredient;

import java.util.List;

public class IngredientGridAdapter extends RecyclerView.Adapter<IngredientGridAdapter.ViewHolder> {
    Context context;
    List<MealIngredient> ingredientList;
    private static final String TAG = "IngridientGridAdapter";

    public IngredientGridAdapter(Context context, List<MealIngredient> ingredientList) {
        this.context = context;
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view  = inflater.inflate(R.layout.grid_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        Log.i(TAG, "onCreateViewHolder: ");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleTxt.setText(ingredientList.get(position).getName());
        holder.measureTxt.setText(ingredientList.get(position).getMeasure());
        Glide.with(context).load(ingredientList.get(position).getImageUrl())
                .apply(new RequestOptions().override(100,100))
                .placeholder(R.drawable.ingredient)
                .error(R.drawable.ingredient)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView titleTxt;
        TextView measureTxt;
        ConstraintLayout constraintLayout;
        public View layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ingredient_image);
            titleTxt = itemView.findViewById(R.id.ingredient_name);
            measureTxt = itemView.findViewById(R.id.ingredient_measure);
        }
    }

    public void setIngredientList(List<MealIngredient> ingredientList){
        this.ingredientList = ingredientList;
    }
}

