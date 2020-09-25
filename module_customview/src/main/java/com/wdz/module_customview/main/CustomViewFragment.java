package com.wdz.module_customview.main;


import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
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

import java.io.IOException;
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
        mainBeanList.clear();
        String[][] array={
                {"自定义View","rgbRing圆","hsv圆","SurfaceView","翻页效果"," TabView",""},
                {"角标效果","Group实现","View实现","","指示器","","viewpage循环"},
                {"字母索引","列表索引","选择器","recyclerView","","",""},
                {"视图","LayoutInflater","瀑布流","","","",""},
                {"View合集","","悬浮窗","Scroller","软键盘","折线图",""},
                {"动画合集","属性动画","splash","","","",""},
                {"Material","抽屉","折叠视图","输入框","","",""},
        };

        for (String[] mArray:array) {
            mainBeanList.add(new MainBean(mArray[0],mArray[1],mArray[2],mArray[3],mArray[4],mArray[5],mArray[6]));
        }

//        MediaPlayer mediaPlayer = new MediaPlayer();
//        AssetFileDescriptor assetFileDescriptor = getResources().openRawResourceFd(R.raw.eason);
//        try {
//            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),assetFileDescriptor.getStartOffset(),assetFileDescriptor.getLength());
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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

                        } else if (viewId == R.id.button4) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_INDICATOR).navigation();
                        } else if (viewId == R.id.button5) {

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

                        } else if (view.getId() == R.id.button4) {

                        } else if (view.getId() == R.id.button5) {

                        } else if (view.getId() == R.id.button6) {

                        }
                        break;
                    case 4:
                        if (view.getId() == R.id.button) {

                        } else if (view.getId() == R.id.button2) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_FLOATING_VIEW).navigation();
                        } else if (view.getId() == R.id.button3) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_SCROLLER).navigation();
                        } else if (view.getId() == R.id.button4) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_EDITTEXT_DEMO).navigation();
                        } else if (view.getId() == R.id.button5) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_LINE_CHART).navigation();
                        } else if (view.getId() == R.id.button6) {

                        }
                        break;

                    case 5:
                        if (view.getId() == R.id.button) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_ANIM_DEMO).navigation();
                            Log.i(TAG,"position:"+position);
                        } else if (view.getId() == R.id.button2) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_MAIN_SPLASH).navigation();
                        } else if (view.getId() == R.id.button3) {

                        } else if (view.getId() == R.id.button4) {

                        } else if (view.getId() == R.id.button5) {

                        } else if (view.getId() == R.id.button6) {

                        }
                        break;

                    case 6:
                        if (view.getId() == R.id.button) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_DRAWERLAYOUT).navigation();
                            Log.i(TAG,"position:"+position);
                        } else if (view.getId() == R.id.button2) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_COORDINATOR).navigation();
                        } else if (view.getId() == R.id.button3) {
                            ARouter.getInstance().build(ARouterConstant.ACTIVITY_TEXT_INPUT).navigation();
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
