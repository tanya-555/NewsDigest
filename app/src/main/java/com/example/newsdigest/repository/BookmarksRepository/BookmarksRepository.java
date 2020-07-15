package com.example.newsdigest.repository.BookmarksRepository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.newsdigest.db.BookmarkDatabase;
import com.example.newsdigest.db.BookmarkOperationsDao;
import com.example.newsdigest.models.BookmarkModel;

import java.util.List;

public class BookmarksRepository {

    private BookmarkDatabase database;
    private BookmarkOperationsDao bookmarkOperationsDao;

    public BookmarksRepository(Application application) {
        database = BookmarkDatabase.getInstance(application);
        bookmarkOperationsDao = database.getBookmarkOperationsDao();
    }

    public LiveData<List<BookmarkModel>> getBookmarks() {
        return bookmarkOperationsDao.getBookmarkList();
    }

    public void insertBookmark(BookmarkModel bookmarkModel) {
        new InsertBookmark(bookmarkOperationsDao).execute(bookmarkModel);
    }

    //Async task to insert bookmark into database
    private static class InsertBookmark extends AsyncTask<BookmarkModel, Void, Void> {

        private BookmarkOperationsDao bookmarkOperationsDao;

        InsertBookmark(BookmarkOperationsDao bookmarkOperationsDao) {
            this.bookmarkOperationsDao = bookmarkOperationsDao;
        }

        @Override
        protected Void doInBackground(final BookmarkModel... params) {
            bookmarkOperationsDao.insertBookmark(params[0]);
            return null;
        }
    }

}
