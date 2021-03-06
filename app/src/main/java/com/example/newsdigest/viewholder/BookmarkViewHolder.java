package com.example.newsdigest.viewholder;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsdigest.databinding.BookmarkItemBinding;
import com.example.newsdigest.models.BookmarkModel;

public class BookmarkViewHolder extends RecyclerView.ViewHolder {

    private BookmarkItemBinding binding;
    public TextView tvSection;

    public BookmarkViewHolder(@NonNull BookmarkItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        tvSection = binding.tvSection;
    }

    public void bindData(BookmarkModel bookmarkModel) {
        binding.setResponse(bookmarkModel);
        binding.executePendingBindings();
    }
}
