package com.example.newsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class categoryListAdapter extends RecyclerView.Adapter<categoryViewHolder> {
    private ArrayList<String> categoryList;
    private categoryItemClicked listiner;

    public categoryListAdapter(ArrayList<String> categoryList,categoryItemClicked listiner) {
        this.categoryList = categoryList;
        this.listiner = listiner;
    }

    // changing the text Appereance in recycler view inside binding by calling this function
    public String changeName(String name){
        if(name == "https://saurav.tech/NewsAPI/everything/cnn.json"){
             return "All";
        } else if (name == "business") {
            return "Business";
        } else if (name == "entertainment") {
            return "Entertainment";
        } else if (name == "general") {
            return "General";
        }else if (name == "health") {
            return "Health";
        }else if (name == "science") {
            return "Science";
        }else if (name == "Sports") {
            return "Business";
        }else if (name == "technology") {
            return "Science & Technology";
        }
        return name;
    }

    @NonNull
    @Override
    public categoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_category, parent, false);
        final categoryViewHolder holder = new categoryViewHolder(view);

        view.setOnClickListener((v) -> {
            listiner.onCategoryClicked(categoryList.get(holder.getAdapterPosition()));
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull categoryViewHolder holder, int position) {
        String currentCategory = categoryList.get(position);
        // changing text appereance while bindig data by calling changing Name

        holder.categoryTextView.setText(changeName(currentCategory));

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}

//holder class
class categoryViewHolder extends RecyclerView.ViewHolder {
TextView categoryTextView = (TextView) itemView.findViewById(R.id.categoryTextView);

    public categoryViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}


// caller is made by the interface
interface categoryItemClicked{
    void onCategoryClicked(String category);
    }
