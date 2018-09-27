package com.example.dezhiwang.studycollection.TurnPage;

import android.graphics.Bitmap;

/**
 * Created by dezhi.wang on 2018/9/26.
 */

public abstract class PageFctory {
    public boolean hasData=false;
    public int pageTotal=0;
    public PageFctory() {
    }
    public abstract void drawPrevious(Bitmap bitmap,int pageNum);
    public abstract void drawCurrent(Bitmap bitmap,int pageNum);
    public abstract void drawNext(Bitmap bitmap,int pageNum);
    public abstract Bitmap getBitmapByIndex(int index);
}
