package com.wdz.module_customview.view.turnpage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.wdz.common.util.BitmapUtils;
import com.wdz.common.util.ScreenUtils;


/**
 * Created by dezhi.wang on 2018/9/26.
 */

public class PicturePageFactory extends PageFctory {
    private Context context;
    private int[] pictIds;

    public PicturePageFactory(Context context,int[] pictIds) {
        this.context=context;
        this.pictIds=pictIds;
        if (pictIds.length>0){
            hasData=true;
            pageTotal=pictIds.length;
        }
    }

    @Override
    public void drawPrevious(Bitmap bitmap, int pageNum) {
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(getBitmapByIndex(pageNum-2),0,0,null);
    }

    @Override
    public void drawCurrent(Bitmap bitmap, int pageNum) {
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(getBitmapByIndex(pageNum-1),0,0,null);
    }

    @Override
    public void drawNext(Bitmap bitmap, int pageNum) {
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(getBitmapByIndex(pageNum),0,0,null);
    }

    @Override
    public Bitmap getBitmapByIndex(int index) {
        if (hasData){
            return BitmapUtils.drawableToBitmap(context.getResources().getDrawable(pictIds[index]), ScreenUtils.getScreenWidth(context),ScreenUtils.getScreenHeight(context));
        }else{
            return null;
        }
    }
}
