package com.example.newsdigest.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsdigest.databinding.NewsItemBinding;
import com.example.newsdigest.models.NewsList;
import com.example.newsdigest.viewholder.NewsViewHolder;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    private static final String TAG = NewsAdapter.class.getName();
    private static final String CONFIRM_BOOKMARK = "Do you want to bookmark this news?";
    private static final String YES = "yes";
    private static final String NO = "no";

    private NewsItemBinding binding;
    private Context context;
    private List<NewsList> resultsList;
    private PublishSubject<String> newsItemClickSubject;
    private PublishSubject<NewsList> newsItemLongClickSubject;

    public NewsAdapter(Context context) {
        this.context = context;
        resultsList = new ArrayList<>();
        newsItemClickSubject = PublishSubject.create();
        newsItemLongClickSubject = PublishSubject.create();
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
        RxView.clicks(holder.itemView).observeOn(AndroidSchedulers.mainThread())
                     .subscribe(s -> {
                         newsItemClickSubject.onNext(news.webUrl);
                     }, e -> {
                         Log.d(TAG, e.getMessage());
                     });
        RxView.longClicks(holder.itemView).observeOn(AndroidSchedulers.mainThread())
                     .subscribe(s -> {
                         openConfirmationDialog(news, position);
                     }, e -> {
                         Log.d(TAG, e.getMessage());
                     });
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
            case "books" : section.setBackgroundColor(Color.parseColor("#7a2464")); break;
            case "education" : section.setBackgroundColor(Color.parseColor("#ffae00")); break;
            default: section.setBackgroundColor(Color.parseColor("#439752")); break;
        }
    }

    public PublishSubject<String> getNewsItemClickSubject() {
        return newsItemClickSubject;
    }

    public PublishSubject<NewsList> getNewsItemLongClickSubject() {
        return newsItemLongClickSubject;
    }

    private void openConfirmationDialog(NewsList news, int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage(CONFIRM_BOOKMARK);
        alertDialog.setCancelable(true);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, YES,
                (dialog, which) -> {
                    newsItemLongClickSubject.onNext(news);
                    alertDialog.dismiss();
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, NO,
                (dialog, which) -> alertDialog.dismiss());
        alertDialog.show();
    }
}
