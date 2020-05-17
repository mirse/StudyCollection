package com.wdz.studycollection.materialdesign.testBehavior;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.wdz.studycollection.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        RecyclerView commentList = findViewById(R.id.comment_list);
//        List<String> list = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            list.add(i + 1 + "");
//        }
//        TypeAdapter adapter = new TypeAdapter(this, list, false);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        commentList.setLayoutManager(layoutManager);
//        commentList.setAdapter(adapter);

    }
}
