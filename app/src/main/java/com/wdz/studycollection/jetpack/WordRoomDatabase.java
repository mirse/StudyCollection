package com.wdz.studycollection.jetpack;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.wdz.studycollection.jetpack.bean.Word;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Word.class},version = 1)
public abstract class WordRoomDatabase extends RoomDatabase {
    private static final String TAG = "WordRoomDatabase";
    public abstract WordDao wordDao();
    private static volatile WordRoomDatabase INSTANCE;

    static WordRoomDatabase getDataBase(final Context context){
        if (INSTANCE==null){
            synchronized (WordRoomDatabase.class){
                if (INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),WordRoomDatabase.class,"word_database").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    //要删除所有内容并在应用程序启动时重新填充数据库
    private static RoomDatabase.Callback sRoomDatabaseCallback= new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Log.i(TAG,"数据库初始化......");
            //由于不能对UI线程执行Room数据库操作
            //new PopulateDbAsync(INSTANCE).execute();
        }
    };


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WordDao mDao;

        PopulateDbAsync(WordRoomDatabase db) {
            mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Word word = new Word("Hello!!");
            mDao.insert(word);
            word = new Word("World");
            mDao.insert(word);
            return null;
        }
    }
}
