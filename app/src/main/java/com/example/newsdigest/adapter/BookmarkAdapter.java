package com.example.newsdigest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsdigest.databinding.BookmarkItemBinding;
import com.example.newsdigest.models.BookmarkModel;
import com.example.newsdigest.viewholder.BookmarkViewHolder;

import java.util.ArrayList;
import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkViewHolder> {

    private BookmarkItemBinding binding;
    private Context context;
    private List<BookmarkModel> bookmarkModelList;

    public BookmarkAdapter(Context context) {
        this.context = context;
        bookmarkModelList = new ArrayList<>();
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = BookmarkItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new BookmarkViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {
        BookmarkModel bookmarkModel = bookmarkModelList.get(position);
        holder.bindData(bookmarkModel);
    }

    @Override
    public int getItemCount() {
        return bookmarkModelList.size();
    }

    public void setBookmarkModelList(List<BookmarkModel> bookmarkModelList) {
        this.bookmarkModelList = bookmarkModelList;
    }
}
