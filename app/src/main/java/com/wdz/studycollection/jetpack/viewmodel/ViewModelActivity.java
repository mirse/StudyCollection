package com.wdz.studycollection.jetpack.viewmodel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wdz.studycollection.R;
import com.wdz.studycollection.jetpack.bean.Word;
import com.wdz.studycollection.jetpack.WordListAdapter;
import com.wdz.studycollection.jetpack.WordViewModel;

import java.util.List;

public class ViewModelActivity extends AppCompatActivity {
    private static final String TAG = "ViewModelActivity";
    @BindView(R.id.recyclerview_word)
    RecyclerView mRecyclerView;
    @BindView(R.id.bt_delete)
    FloatingActionButton mFloatingActionButton;
    private WordViewModel wordViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int DELETE_WORD_ACTIVITY_REQUEST_CODE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_model);
        ButterKnife.bind(this);

        final WordListAdapter adapter = new WordListAdapter(this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        wordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                for (Word word:words) {
                    Log.i(TAG,word.toString());
                }
                adapter.setWords(words);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == DELETE_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_CANCELED) {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }

    }

    @OnClick({R.id.bt_delete})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_delete:
                startActivityForResult(new Intent(this,NewWordActivity.class),DELETE_WORD_ACTIVITY_REQUEST_CODE);
                break;
            default:
                break;
        }
    }
}
