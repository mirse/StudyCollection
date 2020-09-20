package com.wdz.module_architecture.main.jetpack;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.wdz.module_architecture.main.jetpack.bean.Word;


import java.util.List;

//ViewModel则负责保存和处理UI所需的所有数据。
public class WordViewModel extends AndroidViewModel {
    private static final String TAG = "WordViewModel";
    private final LiveData<List<Word>> allWords;
    private final WordRepository wordRepository;

    public WordViewModel(Application application) {
        super(application);
        Log.i(TAG,"WordViewModel 初始化");
        wordRepository = new WordRepository(application);
        allWords = wordRepository.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() { return allWords; }
    public void insert(Word word){
        wordRepository.insert(word);
    }
    public void delete(Word word){
        wordRepository.delete(word);
    }
}
