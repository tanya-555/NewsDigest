package com.example.newsdigest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

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
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    public void setList(List<NewsList> resultsList) {
        this.resultsList = resultsList;
    }
}
