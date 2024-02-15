package com.example.foodplanner.favorites.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.R;
import com.example.foodplanner.home_screen.view.CategoryListAdapter;
import com.example.foodplanner.model.dto.Meal;

import java.util.List;

public class FavListAdapter extends RecyclerView.Adapter<FavListAdapter.ViewHolder>{
    List<Meal> mealList;
    Context context;
    OnFavClickListener listener;
    private static final String TAG = "FavListAdapter";
    public FavListAdapter(Context context, List<Meal> mealList, OnFavClickListener listener) {
        this.mealList = mealList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view  = inflater.inflate(R.layout.large_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        Log.i(TAG, "onCreateViewHolder: ");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mealName.setText(mealList.get(position).getName());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMealClick(mealList.get(position));
            }
        });
        Glide.with(context).load(mealList.get(position).getImageUrl())
                .apply(new RequestOptions().override(350,350))
                .placeholder(R.drawable.downloading)
                .error(R.drawable.broken_image)
                .into(holder.mealImage);
        holder.iconDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteClick(mealList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mealImage;
        TextView mealName;
        CardView iconDelete;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.mealNameItem);
            mealImage = itemView.findViewById(R.id.mealImageItem);
            iconDelete = itemView.findViewById(R.id.delete_fav);
            constraintLayout = itemView.findViewById(R.id.favItemLayout);
        }
    }

    public void setList(List<Meal> mealList){
        this.mealList = mealList;
    }
}
