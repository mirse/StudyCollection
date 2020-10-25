package com.wdz.module_basis.widget.recyclerview.snaphelper;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.wdz.module_basis.R;
import com.wdz.module_basis.R2;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SnapHelperActivity extends AppCompatActivity {
    private static final String TAG = "SnapHelperActivity";

    @BindView(R2.id.myView)
    PageRecyclerView myRecyclerView;
    private List<Item> mList = new ArrayList<>();
    private ImageView[] imageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_basis_activity_snap_helper);
        ButterKnife.bind(this);
        initData();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        myRecyclerView.setAdapter(this,linearLayoutManager,mList);


    }





    private void initData() {
        for (int i=0;i<20;i++){
            mList.add(new Item(String.valueOf(i),false));
        }
    }

    public class Item{
        private String name;
        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Item(String name, boolean isSelect) {
            this.name = name;
            this.isSelect = isSelect;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "name='" + name + '\'' +
                    ", isSelect=" + isSelect +
                    '}';
        }
    }
}
