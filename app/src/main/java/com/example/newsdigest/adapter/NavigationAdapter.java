package com.example.newsdigest.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.newsdigest.R;
import com.example.newsdigest.models.NavigationModel;

public class NavigationAdapter extends ArrayAdapter<NavigationModel> {

    private Context context;
    private int layoutResourceId;
    private NavigationModel model[];

    public NavigationAdapter(Context context, int layoutResourceId, NavigationModel model[]) {
        super(context, layoutResourceId, model);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.model = model;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        listItem = inflater.inflate(layoutResourceId, parent, false);
        ImageView imageViewIcon = (ImageView) listItem.findViewById(R.id.imageViewIcon);
        TextView textViewName = (TextView) listItem.findViewById(R.id.textViewName);
        NavigationModel folder = model[position];
        imageViewIcon.setImageResource(folder.icon);
        textViewName.setText(folder.name);
        return listItem;
    }
}
