package com.example.foodplanner.home_screen.view;

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
import com.example.foodplanner.model.dto.Area;
import com.example.foodplanner.model.dto.Category;

import java.util.List;

public class AreaListAdapter extends RecyclerView.Adapter<AreaListAdapter.ViewHolder> {

        Context context;
        List<Area> areaList;
        OnItemClickListener listener;
        private static final String TAG = "MyAdapter";

        public AreaListAdapter(Context context, List<Area> areaList, OnItemClickListener listener) {
            this.context = context;
            this.areaList = areaList;
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
        public AreaListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view  = inflater.inflate(R.layout.horizontal_list_item, parent, false);
            AreaListAdapter.ViewHolder viewHolder = new AreaListAdapter.ViewHolder(view);
            Log.i(TAG, "onCreateViewHolder: ");
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull AreaListAdapter.ViewHolder holder, final int position) {
            holder.titleTxt.setText(areaList.get(position).getName());
            holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onAreaClicked(areaList.get(position));
                }
            });
            Log.i(TAG, "onBindViewHolder: ");
            Glide.with(context).load(areaList.get(position).getThumbnailUrl())
                    .apply(new RequestOptions().override(700,700))
                    .placeholder(R.drawable.downloading)
                    .error(R.drawable.broken_image)
                    .into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return areaList.size();
        }
        public void setList(List<Area> areaList){
            this.areaList = areaList;
        }
}
