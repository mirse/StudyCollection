package com.wdz.module_architecture.dagger;

import android.widget.TextView;

import org.w3c.dom.Text;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {

    @Provides
    User provideStudent() {
        return new User();
    }

    @Provides
    TextView provideTextView(DaggerDemoActivity daggerDemoActivity){
        TextView textView = new TextView(daggerDemoActivity);
        return textView;
    }
}
