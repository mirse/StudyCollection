package com.wdz.module_basis.main;


import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wdz.common.base.BaseFragment;
import com.wdz.common.bean.MainBean;
import com.wdz.common.constant.ARouterConstant;

import com.wdz.common.util.Log;
import com.wdz.module_basis.MainAdapter;
import com.wdz.module_basis.R;
import com.wdz.module_basis.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 *
 * @author dezhi.wang
 * @date 2018/9/29
 */
@Route(path = ARouterConstant.FRAGMENT_BASIS)
public class BasisFragment extends BaseFragment {
    private static final String TAG = "CommunicationFragment";
    @BindView(R2.id.recyclerview_main)
    RecyclerView recyclerView;
    private List<MainBean> mainBeanList = new ArrayList<>();;
    private MainAdapter mainAdapter;


    @Override
    protected int setLayoutId() {
        return R.layout.module_basis_activity_main;
    }

    @Override
    protected void init() {
        mainBeanList.clear();
        String[][] array={
                {"basis","Fragment","与viewpager","service","","",""},
                {"多媒体","通知","相机","音频解析","","",""},
                {"适配","状态栏","","","","",""},
                {"控件","editText","recycler","grid","输入框","",""},
                {"其他","悬浮窗","dialog","","","",""},
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
                switch (position){
                    case 0:
                        int id = view.getId();
                        if (id == R.id.button) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_FRAGMENT).navigation();
                        } else if (id == R.id.button2) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_VP_AND_FRAG).navigation();
                        } else if (id == R.id.button3) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_SERVICE).navigation();
                        } else if (id == R.id.button4) {

                        } else if (id == R.id.button5) {

                        } else if (id == R.id.button6) {

                        }
                        break;
                    case 1:
                        if (view.getId() == R.id.button) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_NOTIFY).navigation();
                            Log.i(TAG,"position:"+position);
                        } else if (view.getId() == R.id.button2) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_CAMERAX).navigation();
                        } else if (view.getId() == R.id.button3) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_VOICE).navigation();
                        } else if (view.getId() == R.id.button4) {

                        } else if (view.getId() == R.id.button5) {

                        } else if (view.getId() == R.id.button6) {

                        }
                        break;
                    case 2:
                        if (view.getId() == R.id.button) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_MAIN_SPLASH).navigation();
                            Log.i(TAG,"position:"+position);
                        } else if (view.getId() == R.id.button2) {

                        } else if (view.getId() == R.id.button3) {

                        } else if (view.getId() == R.id.button4) {

                        } else if (view.getId() == R.id.button5) {

                        } else if (view.getId() == R.id.button6) {

                        }
                        break;
                    case 3:
                        if (view.getId() == R.id.button) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_EDITTEXT_DEMO).navigation();
                            Log.i(TAG,"position:"+position);
                        } else if (view.getId() == R.id.button2) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_RECYCLER_VIEW).navigation();
                        } else if (view.getId() == R.id.button3) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_GRID_VIEW).navigation();
                        } else if (view.getId() == R.id.button4) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_TEXT_INPUT).navigation();
                        } else if (view.getId() == R.id.button5) {

                        } else if (view.getId() == R.id.button6) {

                        }
                        break;
                    case 4:
                        if (view.getId() == R.id.button) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_FLOATING_VIEW).navigation();

                        } else if (view.getId() == R.id.button2) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_DIALOG_FRAGMENT).navigation();
                        } else if (view.getId() == R.id.button3) {

                        } else if (view.getId() == R.id.button4) {

                        } else if (view.getId() == R.id.button5) {

                        } else if (view.getId() == R.id.button6) {

                        }
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
