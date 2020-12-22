package com.wdz.module_architecture.main;


import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wdz.common.base.BaseFragment;
import com.wdz.common.bean.MainBean;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.common.util.Log;
import com.wdz.module_architecture.R;
import com.wdz.module_architecture.R2;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 *
 * @author dezhi.wang
 * @date 2018/9/29
 */
@Route(path = ARouterConstant.FRAGMENT_ARCHITECTURE)
public class ArchitectureFragment extends BaseFragment {
    private static final String TAG = "CommunicationFragment";
    @BindView(R2.id.recyclerview_main)
    RecyclerView recyclerView;
    private List<MainBean> mainBeanList = new ArrayList<>();;
    private MainAdapter mainAdapter;


    @Override
    protected int setLayoutId() {
        return R.layout.module_architecture_activity_main;
    }

    @Override
    protected void init() {
        mainBeanList.clear();
        String[][] array={
                {"Jetpack","ViewModel","RxJava","Dagger","MVVM","",""},
                {"设计模式","单例","建造者","原型","","",""},
        };

        for (String[] mArray:array) {
            mainBeanList.add(new MainBean(mArray[0],mArray[1],mArray[2],mArray[3],mArray[4],mArray[5],mArray[6]));
        }
    }

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        mainAdapter = new MainAdapter(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mainAdapter);
        mainAdapter.setOnItemClickListener(new MainAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int id = view.getId();
                switch (position){
                    case 0:
                        if (id == R.id.button) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_VIEW_MODEL).navigation();
                        } else if (id == R.id.button2) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_NEW_WORD).navigation();
                        } else if (id == R.id.button3) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_DAGGER).navigation();
                        } else if (id == R.id.button4) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_MVVMDEMO).navigation();
                        } else if (id == R.id.button5) {

                        } else if (id == R.id.button6) {

                        }
                        break;
                    case 1:

                        if (id == R.id.button) {

                        } else if (id == R.id.button2) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_BUILDER_DEMO).navigation();
                        } else if (id == R.id.button3) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_PROTOTYPE_DEMO).navigation();
                        } else if (id == R.id.button4) {

                        } else if (id == R.id.button5) {

                        } else if (id == R.id.button6) {

                        }
                        break;
                    case 2:

                        break;
                    case 3:

                        break;


                    default:
                        break;

                }
            }
        });

        mainAdapter.setDatas(mainBeanList);

    }

    @Override
    protected void initData() {

    }


}
