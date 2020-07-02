package com.example.newsdigest.repository.BookmarksRepository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.newsdigest.db.BookmarkDatabase;
import com.example.newsdigest.db.BookmarkOperationsDao;
import com.example.newsdigest.models.BookmarkModel;

import java.util.List;

public class BookmarksRepository {

    private BookmarkDatabase database;
    private MutableLiveData<List<BookmarkModel>> bookmarks;
    private BookmarkOperationsDao bookmarkOperationsDao;

    public BookmarksRepository(Application application) {
        database = BookmarkDatabase.getInstance(application);
        bookmarkOperationsDao = database.getBookmarkOperationsDao();
    }

    public MutableLiveData<List<BookmarkModel>> getBookmarks() {
        return bookmarks;
    }

    public void insertBookmark(BookmarkModel bookmarkModel) {
        new InsertBookmark(bookmarkOperationsDao).execute(bookmarkModel);
    }

    private static class InsertBookmark extends AsyncTask<BookmarkModel, Void, Void> {

        private BookmarkOperationsDao bookmarkOperationsDao;
        InsertBookmark(BookmarkOperationsDao bookmarkOperationsDao) {
            this.bookmarkOperationsDao = bookmarkOperationsDao;
        }

        @Override
        protected Void doInBackground(final BookmarkModel ... params) {
            bookmarkOperationsDao.insertBookmark(params[0]);
            return null;
        }
    }
}
