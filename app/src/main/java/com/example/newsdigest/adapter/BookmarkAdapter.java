package com.example.newsdigest.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

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
        setSectionColor(holder.tvSection);
    }

    @Override
    public int getItemCount() {
        return bookmarkModelList.size();
    }

    public void setBookmarkModelList(List<BookmarkModel> bookmarkModelList) {
        this.bookmarkModelList = bookmarkModelList;
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
            case "books" : section.setBackgroundColor(Color.parseColor("#7a2464")); break;
            case "education" : section.setBackgroundColor(Color.parseColor("#ffae00")); break;
            default: section.setBackgroundColor(Color.parseColor("#439752")); break;
        }
    }
}
