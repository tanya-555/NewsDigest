package com.example.newsdigest.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsdigest.databinding.NewsItemBinding;
import com.example.newsdigest.models.NewsList;
import com.example.newsdigest.viewholder.NewsViewHolder;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    private static final String TAG = NewsAdapter.class.getName();

    private NewsItemBinding binding;
    private Context context;
    private List<NewsList> resultsList;

    public NewsAdapter(Context context) {
        this.context = context;
        resultsList = new ArrayList<>();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = NewsItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new NewsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsList news = resultsList.get(position);
        holder.bindData(news);
        setSectionColor(holder.tvSection);
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    public void setList(List<NewsList> resultsList) {
        this.resultsList = resultsList;
    }
    
    private void setSectionColor(TextView section) {
        switch (section.getText().toString().toLowerCase()) {
            case "us news": section.setBackgroundColor(Color.parseColor("#9a1f19")); break;
            case "football" : section.setBackgroundColor(Color.parseColor("#33723b")); break;
            case "world news" : section.setBackgroundColor(Color.parseColor("#212a6f")); break;
            case "politics" : section.setBackgroundColor(Color.parseColor("#79268e")); break;
            case "opinion" : section.setBackgroundColor(Color.parseColor("#827043")); break;
            case "sport" : section.setBackgroundColor(Color.parseColor("#965959")); break;
            case "business" : section.setBackgroundColor(Color.parseColor("#50519a")); break;
            case "uk news": section.setBackgroundColor(Color.parseColor("#329590")); break;
            default: section.setBackgroundColor(Color.parseColor("#439752")); break;
        }
    }
}
