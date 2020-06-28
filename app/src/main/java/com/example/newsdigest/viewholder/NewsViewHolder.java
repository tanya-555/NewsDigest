package com.example.newsdigest.viewholder;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.newsdigest.databinding.NewsItemBinding;
import com.example.newsdigest.models.NewsList;

public class NewsViewHolder extends ViewHolder {

    private NewsItemBinding binding;

    public NewsViewHolder(NewsItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindData(NewsList result) {
        binding.setResponse(result);
        binding.executePendingBindings();
    }
}
