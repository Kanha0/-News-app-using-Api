package com.example.newsapp;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class NewsListAdapter extends RecyclerView.Adapter<newsViewHolder> {
    private ArrayList<news> items = new ArrayList<>();
    private newsItemclicked listiner;
    public NewsListAdapter( newsItemclicked listiner) {
        this.listiner = listiner;
    }


    @NonNull
    @Override
    public newsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent,false);
       final newsViewHolder viewHolder = new newsViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            listiner.onItemClicked(items.get(viewHolder.getAdapterPosition()));
            }
        });
//
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final newsViewHolder holder, int position) {
    news currentItem = items.get(position);
    holder.title.setText(currentItem.getTitle()); // binding title
    holder.author.setText(currentItem.getAuthor()); // binding author
    holder.progressbar.setVisibility(View.VISIBLE);
    Glide.with(holder.itemView.getContext()).load(items.get(position).getUrlToImage()).listener(new RequestListener<Drawable>() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            holder.progressbar.setVisibility(View.GONE);
            return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            holder.progressbar.setVisibility(View.GONE);
            return false;
        }
    }).into(holder.imageView);  // binding image url

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void  updateNews(ArrayList<news> updatedNews){

        items.clear();

        items.addAll(updatedNews);

        //to re call the above three function i.e. to update the adapter we call bellow method which re call the adapter with new values
        notifyDataSetChanged();
    }


    //to re call the above three function i.e. to update the adapter we call bellow function which re call the adapter with new values

}

class newsViewHolder extends RecyclerView.ViewHolder{
    TextView title = (TextView) itemView.findViewById(R.id.title);
    TextView author = (TextView) itemView.findViewById(R.id.author);
    ImageView imageView = (ImageView) itemView.findViewById(R.id.imageview);
    ProgressBar progressbar = (ProgressBar) itemView.findViewById(R.id.progressbar);

    public newsViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}

// caller is made by the interface
interface newsItemclicked{

     void onItemClicked(news item);
}
