package com.wdz.module_architecture.navigation;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_architecture.R;
import com.wdz.module_architecture.R2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import butterknife.BindView;
import butterknife.ButterKnife;
//https://juejin.cn/post/6901572812990283790/ BottomNavigationView生命周期解决方法

@Route(path = ARouterConstant.ACTIVITY_NAVIGATION)
public class NavigationActivity extends AppCompatActivity {

//    @BindView(R2.id.nav_view)
//    BottomNavigationView navView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        //设置图标颜色
        navView.setItemIconTintList(null);

        Resources resource = getResources();
        ColorStateList csl = resource.getColorStateList(R.color.main_pager_tab_text_color_selector);
        //设置文字颜色
        navView.setItemTextColor(csl);

//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //设置与AppBar关联
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

}
