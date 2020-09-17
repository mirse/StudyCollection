package com.wdz.module_communication.main;


import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wdz.common.base.BaseFragment;
import com.wdz.common.bean.MainBean;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.common.util.Log;
import com.wdz.module_communication.R;
import com.wdz.module_communication.R2;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 *
 * @author dezhi.wang
 * @date 2018/9/29
 */
@Route(path = ARouterConstant.FRAGMENT_CUMMUNICATION)
public class CommunicationFragment extends BaseFragment {
    private static final String TAG = "CommunicationFragment";
    @BindView(R2.id.recyclerview_main)
    RecyclerView recyclerView;
    private List<MainBean> mainBeanList = new ArrayList<>();;
    private MainAdapter mainAdapter;


    @Override
    protected int setLayoutId() {
        return R.layout.module_communication_activity_main;
    }

    @Override
    protected void init() {
        String[][] array={
                {"网络通信","Socket通信","AsyncTask","Handler","handler切换线程","OkHttp","Glide"},
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
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_LETTER_INDEX).navigation();
                            Log.i(TAG,"position:"+position);
                        } else if (i == R.id.button2) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_DATE_PICKER).navigation();
                        } else if (i == R.id.button3) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_RECYCLER_VIEW).navigation();
                        } else if (i == R.id.button4) {

                        } else if (i == R.id.button5) {

                        } else if (i == R.id.button6) {

                        }
                        break;
                    case 3:
                        if (view.getId() == R.id.button) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_LAYOUT_INFLATER).navigation();
                            Log.i(TAG,"position:"+position);
                        } else if (view.getId() == R.id.button2) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_GRID_VIEW).navigation();
                        } else if (view.getId() == R.id.button3) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_FIX_PROBLEM).navigation();
                        } else if (view.getId() == R.id.button4) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_FRAGMENT).navigation();
                        } else if (view.getId() == R.id.button5) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_VP_AND_FRAG).navigation();
                        } else if (view.getId() == R.id.button6) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_SAVEINSTANCE).navigation();
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
