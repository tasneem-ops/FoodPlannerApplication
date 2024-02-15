package com.example.foodplanner.week_plan.view;

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
import com.example.foodplanner.home_screen.view.OnItemClickListener;
import com.example.foodplanner.model.dto.Category;
import com.example.foodplanner.model.dto.PlanMeal;

import java.util.List;

public class PlanMealListAdapter extends RecyclerView.Adapter<PlanMealListAdapter.ViewHolder>{
    Context context;
    List<PlanMeal> planMeals;
    OnPlanMealClickListener listener;
    private static final String TAG = "MyAdapter";

    public PlanMealListAdapter(Context context, List<PlanMeal> planMeals, OnPlanMealClickListener listener) {
        this.context = context;
        this.planMeals = planMeals;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView titleTxt;
        ConstraintLayout constraintLayout;
        public View layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_thumbnail);
            titleTxt = itemView.findViewById(R.id.item_name);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view  = inflater.inflate(R.layout.horizontal_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        Log.i(TAG, "onCreateViewHolder: ");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.titleTxt.setText(planMeals.get(position).getName());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onPlanMealClicked(planMeals.get(position));
            }
        });
        Log.i(TAG, "onBindViewHolder: ");
        Glide.with(context).load(planMeals.get(position).getImageUrl())
                .apply(new RequestOptions().override(320,200))
                .placeholder(R.drawable.downloading)
                .error(R.drawable.broken_image)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return planMeals.size();
    }
    public void setList(List<PlanMeal> planMeals){
        this.planMeals = planMeals;
    }
}
