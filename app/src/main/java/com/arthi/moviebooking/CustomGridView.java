package com.arthi.moviebooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomGridView extends RecyclerView.Adapter<CustomGridView.MyViewHolder> {
    //    Context context;
    List<MoviesContent> data;
    HomePage homePage;

    public CustomGridView(HomePage homePage, List<MoviesContent> data) {
//        this.context = context;
        this.data = data;
        this.homePage = homePage;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(homePage).inflate(R.layout.grid_item, parent, false);
        v.setOnClickListener(homePage);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + data.get(position).getPoster_path()).resize(500, 700).into(holder.movie_image);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView movie_image;
        public TextView rating;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            movie_image = itemView.findViewById(R.id.grid_image);

        }
    }
}
