package com.wdz.studycollection.jetpack;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;
//Repository管理查询线程
public class WordRepository {

    private final WordDao wordDao;
    private final LiveData<List<Word>> allWords;

    WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDataBase(application);
        wordDao = db.wordDao();
        allWords = wordDao.getAllWords();
    }

    LiveData<List<Word>> getAllWords() {
        return allWords;
    }

    public void insert(Word word){
        new insertAsyncTask(wordDao).execute(word);
    }

    public static class insertAsyncTask extends AsyncTask<Word,Void,Void>{
        private WordDao mSyncTaskDao;

        public insertAsyncTask(WordDao wordDao) {
            mSyncTaskDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            mSyncTaskDao.insert(words[0]);
            return null;
        }
    }

}
