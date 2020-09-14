package com.wdz.module_customview.main;


import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wdz.common.util.Log;
import com.wdz.common.base.BaseFragment;
import com.wdz.common.bean.MainBean;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_customview.R;
import com.wdz.module_customview.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 *
 * @author dezhi.wang
 * @date 2018/9/29
 */
@Route(path = ARouterConstant.FRAGMENT_CUSTOM_VIEW)
public class CustomViewFragment extends BaseFragment {
    private static final String TAG = "CustomViewFragment";
    @BindView(R2.id.recyclerview_main)
    RecyclerView recyclerView;
    private List<MainBean> mainBeanList = new ArrayList<>();;
    private MainAdapter mainAdapter;


    @Override
    protected int setLayoutId() {
        return R.layout.module_customview_fragment1;
    }

    @Override
    protected void init() {
        String[][] array={
                {"自定义View","rgbRing圆","argb圆","hsv圆","SurfaceView","翻页效果","TabView"},
                {"角标效果","Group实现","View实现","ImageV实现","指示器","fragment循环","viewpage循环"},
                {"字母索引","列表索引","Login-mvp","mvp_test","save_test","选择器","recyclerView"},
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
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_RING_COLOR_PICKER).navigation();
                        } else if (id == R.id.button2) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_HSV_COLOR_PICKER).navigation();
                        } else if (id == R.id.button3) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_SURFACE_VIEW).navigation();
                        } else if (id == R.id.button4) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_PAGE).navigation();
                        } else if (id == R.id.button5) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_TAB_VIEW).navigation();
                        } else if (id == R.id.button6) {
                            //ARouter.getInstance().build(ARouterConstant.ACTIVITY_RING_COLOR_PICKER).navigation();
                        }
                        break;
                    case 1:
                        int viewId = view.getId();
                        if (viewId == R.id.button) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_DRAWABLE_WRAP).navigation();
                            Log.i(TAG,"position:"+position);
                        } else if (viewId == R.id.button2) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_DRAWABLE_ICON).navigation();
                        } else if (viewId == R.id.button3) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_DRAWABLE_ICON_IV).navigation();
                        } else if (viewId == R.id.button4) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_INDICATOR).navigation();
                        } else if (viewId == R.id.button5) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_FRAGMENT_PAGER).navigation();
                        } else if (viewId == R.id.button6) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_IMAGE_RECYCLER).navigation();
                        }
                        break;
                    case 2:
                        int i = view.getId();
                        if (i == R.id.button) {
                            Log.i(TAG,"position:"+position);
                        } else if (i == R.id.button2) {

                        } else if (i == R.id.button3) {

                        } else if (i == R.id.button4) {

                        } else if (i == R.id.button5) {

                        } else if (i == R.id.button6) {

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
