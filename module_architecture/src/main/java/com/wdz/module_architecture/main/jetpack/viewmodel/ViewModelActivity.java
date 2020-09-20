package com.wdz.module_architecture.main.jetpack.viewmodel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_architecture.R;
import com.wdz.module_architecture.R2;
import com.wdz.module_architecture.main.jetpack.WordListAdapter;
import com.wdz.module_architecture.main.jetpack.WordViewModel;
import com.wdz.module_architecture.main.jetpack.bean.Word;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
@Route(path = ARouterConstant.ACTIVITY_VIEW_MODEL)
public class ViewModelActivity extends AppCompatActivity {
    private static final String TAG = "ViewModelActivity";
    @BindView(R2.id.recyclerview_word)
    RecyclerView mRecyclerView;
    @BindView(R2.id.bt_delete)
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
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DELETE_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_CANCELED) {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }

    }

    @OnClick({R2.id.bt_delete})
    public void onClick(View view){
        if (view.getId() == R.id.bt_delete) {
            startActivityForResult(new Intent(this, NewWordActivity.class), DELETE_WORD_ACTIVITY_REQUEST_CODE);
        }
    }
}
