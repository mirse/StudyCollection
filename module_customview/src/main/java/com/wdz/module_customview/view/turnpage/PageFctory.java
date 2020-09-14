package com.wdz.module_customview.view.turnpage;

import android.graphics.Bitmap;

/**
 * Created by dezhi.wang on 2018/9/26.
 * 抽象类PageFactory
 */

public abstract class PageFctory {
    public boolean hasData=false;
    public int pageTotal=0;
    public PageFctory() {
    }
    //
    public abstract void drawPrevious(Bitmap bitmap,int pageNum);
    public abstract void drawCurrent(Bitmap bitmap,int pageNum);
    public abstract void drawNext(Bitmap bitmap,int pageNum);
    public abstract Bitmap getBitmapByIndex(int index);
}
