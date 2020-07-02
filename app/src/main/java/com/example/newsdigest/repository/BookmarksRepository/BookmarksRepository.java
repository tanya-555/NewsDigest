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
    private MutableLiveData<List<BookmarkModel>> bookmarkLiveData;
    public List<BookmarkModel> bookmarks;
    private BookmarkOperationsDao bookmarkOperationsDao;

    public BookmarksRepository(Application application) {
        database = BookmarkDatabase.getInstance(application);
        bookmarkOperationsDao = database.getBookmarkOperationsDao();
        bookmarkLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<BookmarkModel>> getBookmarks() {
        new FetchBookmarks(bookmarkOperationsDao).execute();
        bookmarkLiveData.postValue(bookmarks);
        return bookmarkLiveData;
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

    //Async Task to list all bookmarks
    private class FetchBookmarks extends AsyncTask<Void, Void, Void> {

        private BookmarkOperationsDao bookmarkOperationsDao;

        FetchBookmarks(BookmarkOperationsDao bookmarkOperationsDao) {
            this.bookmarkOperationsDao = bookmarkOperationsDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            bookmarks = bookmarkOperationsDao.getBookmarkList();
            return null;
        }
    }

}
