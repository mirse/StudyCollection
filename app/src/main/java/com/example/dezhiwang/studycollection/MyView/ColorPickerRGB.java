package com.example.dezhiwang.studycollection.MyView;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.dezhiwang.studycollection.R;


/**
 * Created by wdz on 2018/8/29.
 */

public class ColorPickerRGB extends View {

    private static final String STATE="state";
    private static final String STATE_ANGLE="angle";

    private int circleWidth;
//    private static final int[] COLOR=new int[]{
//            Color.BLUE,Color.YELLOW,Color.RED,Color.GREEN,Color.CYAN,Color.DKGRAY
//    };

    private static final int[] COLOR=new int[]{
            Color.RED,Color.GREEN,Color.BLUE,Color.RED
    };

//    private static final int[] COLOR = new int[] { 0xFFFF0000, 0xFFFF00FF,
//            0xFF0000FF, 0xFF00FFFF, 0xFF00FF00, 0xFFFFFF00, 0xFFFF0000 };
    //    private static final int[] COLOR = new int[] { 0xFFFF0000, 0xFFFF00FF,
//            0xFF0000FF, 0xFF00FFFF, 0xFF00FF00, 0xFFFFFF00, 0xFFFF0000 };
    private Paint mPaint;
    private int pointWidth;
    private float offset;
    private Paint pointPaint;
    private float mAngle;
    private float preAngle;
    private float AngleCount;
    private float mSlopX;
    private float mSlopY;
    private boolean isMoving;
    private int mColor;
    private MyColor myColor;
    private OnColorChangeListener colorChangeListener;
    private Rect rect;
    private int width;
    private int height;
    private String text;
    private int level;
    private int prelevel;
    private int mcircleWidth;
    private int centerX=0;
    private int centerY=0;
    private double r;
    private boolean isMove;
    private int lastY;
    private int lastX;
    private int offsetX;
    private int offsetY;
    private Paint radialPaint;
    private Paint textPaint;
    private Paint backPaint;

    public ColorPickerRGB(Context context) {
        super(context);
        init(null,0);
    }

    public ColorPickerRGB(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs,0);
    }

    public ColorPickerRGB(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs,defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
//        Log.i("text","init!!!!!!!!!!!!!");
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ColorPickerRGB, defStyleAttr, 0);
        final Resources b = getContext().getResources();
        circleWidth = typedArray.getDimensionPixelSize(R.styleable.ColorPickerRGB_out_rad, b.getDimensionPixelSize(R.dimen.color_wheel_radius));

        mcircleWidth = circleWidth;
        pointWidth = typedArray.getDimensionPixelSize(R.styleable.ColorPickerRGB_in_rad, b.getDimensionPixelSize(R.dimen.color_pointer_radius));
        typedArray.recycle();

        rect = new Rect();
        myColor = new MyColor();
        AngleCount = (float) (0); //  -π/2   初始点的位置
        SweepGradient sweepGradient = new SweepGradient(0, 0, COLOR, null);
        RadialGradient radialGradient = new RadialGradient(0, 0, mcircleWidth, Color.WHITE, 0x00FFFFFF, Shader.TileMode.CLAMP);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setShader(sweepGradient);

        pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointPaint.setColor(Color.BLACK);
        //text =myColor.getA()+" "+myColor.getR()+" "+myColor.getG()+" "+myColor.getB();
        text=toHexFromColor(myColor);
        pointPaint.getTextBounds(text,0, text.length(),rect);
        width = rect.right - rect.left;
        height = rect.bottom - rect.height();

        radialPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        radialPaint.setShader(radialGradient);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(40);
        textPaint.setAntiAlias(true);
        backPaint = new Paint();
        backPaint.setColor(Color.GRAY);
        backPaint.setAntiAlias(true);

    }

    private int calculateColor(float mAngle,double R) {      //1、2象限0-π   3、4象限-π-0
//        int v = (int)(mAngle / (Math.PI * 2));
//        if (v>0){
//            mAngle=(float) (mAngle-Math.PI*2*v);
//        }

        float unit = (float)(mAngle / (Math.PI * 2 ));   //0~0.5 -0.5~0
     //  Log.i("text","unit"+unit);
        if (unit<0){
            unit+=1;
        }
        if (unit<0){
            mColor=COLOR[0];
            myColor=new MyColor();
             Log.i("text","<<<<0?????????");
            myColor.setA(Color.alpha(mColor));
            myColor.setR(Color.red(mColor));
            myColor.setG(Color.green(mColor));
            myColor.setB(Color.blue(mColor));

            return COLOR[0];
        }
        if (unit==0&&R==0){
            myColor=new MyColor();
            myColor.setA(0);
            myColor.setR(255);
            myColor.setG(255);
            myColor.setB(255);
            return COLOR[0];
        }
        if (unit>=1){
            mColor=COLOR[COLOR.length-1];
          //  Log.i("text",">>>>!?????????");
            myColor=new MyColor();
            myColor.setA(Color.alpha(mColor));
            myColor.setR(Color.red(mColor));
            myColor.setG(Color.green(mColor));
            myColor.setB(Color.blue(mColor));

            return COLOR[COLOR.length-1];
        }
        float p = unit * (COLOR.length - 1);  //unit  0~1    p 0~5

        int i = (int) p;
        level = i;
        p-=i;   //p 0~1

        int c0=COLOR[i];
        int c1=COLOR[i+1];
     //   int a=ave(Color.alpha(c0),Color.alpha(c1),p);

        int a = (int) Math.round ((255*(R/mcircleWidth)));
        if (a>=255){
            a=255;
        }

        int r=ave(Color.red(c0),Color.red(c1),p);
        int g=ave(Color.green(c0),Color.green(c1),p);
        int b=ave(Color.blue(c0),Color.blue(c1),p);
        mColor=Color.argb(a,r,g,b);
        myColor=new MyColor();
        myColor.setA(a);
        myColor.setR(r);
        myColor.setG(g);
        myColor.setB(b);

        return mColor;
    }

    private int ave(int s, int s1, float p) {
        return s+Math.round((s1-s)*p);
    }


    //当父View是Relativelayout时，
    //当子view是textview时且 android:layout_width="wrap_content" ，mParent.requestLayout()，迫使父View重新onmeasure,
    //父view measureChild,子View 重新onmeasure

    /* 最简单的映射关系是：
        * wrap_parent -> MeasureSpec.AT_MOST
        * match_parent -> MeasureSpec.EXACTLY
        *具体值 -> MeasureSpec.EXACTLY
        但是上面代码由于放在 RelativeLayout 中，RelativeLayout 是一个比较复杂的 ViewGroup，
        其中子 view 的大小不仅跟 layout_width、layout_height 属性相关，
        还更很多属性有关系，所以会改变上述映射情况，使结果变得特别复杂。


      MeasureSpec不是唯一由view的LayoutParams来确定的，view的LayoutParams需要和父容器一起才能决定view的MeasureSpec，
      从而进一步决定view的宽/高关系。（参考开发艺术探索P180页代码），只要提供父容器的MeasureSpec和子元素的LayoutParams，
      就可以快速的确定出子元素的MeasureSpec了，有了MeasureSpec就可以进一步确定出子元素测量后的大小了

      childDimension  父view getChildMeasureSpec决定
        */

//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//        Log.i("text","onLayout");
//    }

//
//    @Override
//    public void layout(int l, int t, int r, int b) {
//
//        Log.i("text","layout");
//        super.layout(l, t, r, b);
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    //    Log.i("text","onmeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int RealSize=2*(circleWidth+pointWidth);
//        Log.i("text","RealSize========="+RealSize);
        int width;
        int height;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode==MeasureSpec.EXACTLY){//match
            width=widthSize;    //自定义View wrap  不变
        }else if (widthMode==MeasureSpec.AT_MOST){
            width=RealSize;
        }else{
            width=RealSize;
        }
        if (heightMode==MeasureSpec.EXACTLY){
            height=heightSize;
        }else if (heightMode==MeasureSpec.AT_MOST){
            height=RealSize;  //自定义View wrap
        }else{
            height=RealSize;
        }
        int min = Math.min(width, height);
        offset = min * 0.5f;
      //  Log.i("text","min=="+min);
      //  Log.i("text","pointwidth="+pointWidth);
       // mcircleWidth=min/2-pointWidth/2;
//        Log.i("text","mcircleWidth"+mcircleWidth);
        setMeasuredDimension(min,min);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(offset,offset);

        canvas.drawCircle(0,0,mcircleWidth,mPaint);

        canvas.drawCircle(0,0,mcircleWidth,radialPaint);
        //canvas.drawCircle(0,0,circleWidth,mPaint);
        canvas.drawCircle(centerX,centerY,pointWidth,pointPaint);

        canvas.drawLine(-mcircleWidth,0,mcircleWidth,0,pointPaint);//中心轴坐标
        canvas.drawLine(0,-mcircleWidth,0,mcircleWidth,pointPaint);

        pointPaint.setTextSize(40);

        //text =myColor.getA()+" "+myColor.getR()+" "+myColor.getG()+" "+myColor.getB();
        text=toHexFromColor(myColor);

        //Log.i("text","myColor.getA()="+myColor.getA()+" myColor.getR()"+myColor.getR());
      //  text =myColor.getR()+" "+myColor.getG()+" "+myColor.getB();
        pointPaint.getTextBounds(text,0, text.length(),rect);
        width = rect.right - rect.left;
        height = rect.bottom - rect.top;
        //canvas.drawRect(centerX-width/2,centerY-pointWidth-height-height/2,centerX+width/2,centerY-pointWidth-height/2,mPaint);
        Path path = new Path();
        path.moveTo(centerX,centerY-pointWidth);
        path.lineTo(centerX-width/6,centerY-pointWidth-height/2+pointWidth/4);
        path.lineTo(centerX-width/2-pointWidth,centerY-pointWidth-height/2+pointWidth/4);
        path.lineTo(centerX-width/2-pointWidth,centerY-pointWidth-height-height/2-pointWidth/4);
        path.lineTo(centerX+width/2+pointWidth,centerY-pointWidth-height-height/2-pointWidth/4);
        path.lineTo(centerX+width/2+pointWidth,centerY-pointWidth-height/2+pointWidth/4);
        path.lineTo(centerX+width/6,centerY-pointWidth-height/2+pointWidth/4);
        path.close();
        canvas.drawPath(path,backPaint);
        canvas.drawText(text,centerX-width/2,centerY-pointWidth-height/2,textPaint);//文字起点坐标
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        int  x = (int) (event.getX()-offset);
        int  y = (int) (event.getY() - offset);
     //   Log.i("text","X="+x+" Y="+y+" centerX"+centerX+" centerY"+centerY);
        r = Math.sqrt(x * x + y * y);
       // Log.i("text","r=="+r);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                double distance = Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));
                if (distance<30){//点击圆内圆点位置
                    isMove = true;
                    lastX = x;
                    lastY = y;
                }
                else if(r<circleWidth){//点击圆内非圆点位置
                    centerX=x;
                    centerY=y;
                    lastX = x;
                    lastY = y;
                    isMove=true;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:

                if (isMove){

                    if (r >mcircleWidth){//滑动半径大于实际半径
                        x*=mcircleWidth/ r;
                        y*=mcircleWidth/ r;
                        centerX=x;
                        centerY=y;
                        lastX=x;
                        lastY=y;
                        invalidate();
                    }else {//处于圆内
                        offsetX = x - lastX;
                        offsetY = y - lastY;
                        centerX += offsetX;
                        centerY += offsetY;
                        lastX = x;
                        lastY = y;
                        invalidate();
                    }

                    mAngle=(float)Math.atan2(y,x);   //1、2象限0-π   3、4象限-π-0

                  //  pointPaint.setColor(calculateColor(mAngle));
                   // Log.i("text","-----mAngle"+mAngle);
                    if (mAngle<0){
                        mAngle+=Math.PI*2;
                    }
                   // Log.i("text","mAngle"+mAngle);
                    calculateColor(mAngle,r);
                    invalidate();
                     //colorChangeListener.onRGBChange(myColor);
                }
                break;
            case MotionEvent.ACTION_UP:
                isMoving=false;
                invalidate();
                break;
        }
        return true;
    }

    /**
     * @param color
     * @return color转成十六进制
     */
    private static String toHexFromColor(MyColor color){
        String r,g,b,a;
        StringBuilder su = new StringBuilder();
        a=Integer.toHexString(color.getA());
        r = Integer.toHexString(color.getR());
        g = Integer.toHexString(color.getG());
        b = Integer.toHexString(color.getB());
        a = a.length() == 1 ? "0" + a : a;
        r = r.length() == 1 ? "0" + r : r;
        g = g.length() ==1 ? "0" +g : g;
        b = b.length() == 1 ? "0" + b : b;
        a = a.toUpperCase();
        r = r.toUpperCase();
        g = g.toUpperCase();
        b = b.toUpperCase();
        su.append("#");
        su.append(a);
        su.append(r);
        su.append(g);
        su.append(b);		//0xFF0000FF
        return su.toString();
    }




    public void setonColorChange(OnColorChangeListener colorChangeListener){
        this.colorChangeListener=colorChangeListener;
    }
    public interface OnColorChangeListener{
        void onRGBChange(MyColor myColor);
        // void onRGBChange(boolean success);
    }

    //防止旋转设备数据丢失
    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE,parcelable);
        bundle.putFloat(STATE_ANGLE,mAngle);
        return bundle;

    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle bundle = (Bundle) state;
        Parcelable parcelable = bundle.getParcelable(STATE);
        super.onRestoreInstanceState(parcelable);
        AngleCount = bundle.getFloat(STATE_ANGLE);
       // pointPaint.setColor(calculateColor(AngleCount+(float) (-Math.PI / 2)));
    }
}
