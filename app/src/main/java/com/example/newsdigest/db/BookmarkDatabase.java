package com.example.newsdigest.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.newsdigest.models.BookmarkModel;

@Database(entities = BookmarkModel.class, exportSchema = false, version = 1)
abstract public class BookmarkDatabase extends RoomDatabase  {

    private static final String DB_NAME = "myDatabase";
    private static BookmarkDatabase instance;

    public static synchronized BookmarkDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), BookmarkDatabase.class, DB_NAME)
                           .fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public abstract BookmarkOperationsDao getBookmarkOperationsDao();
}
