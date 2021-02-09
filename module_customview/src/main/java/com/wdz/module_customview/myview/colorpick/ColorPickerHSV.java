package com.wdz.module_customview.myview.colorpick;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.wdz.common.util.ColorUtils;
import com.wdz.module_customview.R;


/**
 *  padding未做适配
 * @author wdz
 * @date 2018/9/14
 */

public class ColorPickerHSV extends View {
    private static final String TAG = "ColorPickerHSV";
    Handler handler = new Handler();
    private final String BLACK = "000000";
    private final String FFBLACK = "ff000000";

    private static final int[] COLOR = new int[]{
            Color.rgb(255, 0, 0), Color.rgb(255, 0, 255),
            Color.rgb(0, 0, 255), Color.rgb(0, 255, 255),
            Color.rgb(0, 255, 0), Color.rgb(255, 255, 0),
            Color.rgb(255, 0, 0)



    };

    private int circleRadius;
    private int offsetX;
    private int offsetY;
    private Paint sPaint;
    private Paint mPaint;
    private Paint iPaint;
    private Paint innerCirclePaint;
    private int centerX=0;
    private int centerY=0;
    private int innerCircleRadius;
    private boolean isMove=false;
    private int lastY;
    private int lastX;
    private int offset;
    private double r;
    private onMoveListener onMoveListener;
    private int red;
    private int green;
    private int blue;
    /**
     * 圆点指示器是否可见
     */
    private boolean mIsVisible = true;
    private float realRadius;

    public ColorPickerHSV(Context context) {
        super(context);
        init(null,0);
    }

    public ColorPickerHSV(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs,0);
    }

    public ColorPickerHSV(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs,defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ColorPickerHSV, defStyleAttr, 0);
        Resources resources = getContext().getResources();
        circleRadius = typedArray.getDimensionPixelSize(R.styleable.ColorPickerHSV_color_rad, resources.getDimensionPixelSize(R.dimen.color_wheel_radius));
        innerCircleRadius = typedArray.getDimensionPixelSize(R.styleable.ColorPickerHSV_inner_rad, resources.getDimensionPixelSize(R.dimen.color_pointer_radius));
        typedArray.recycle();
        initPaint();

    }

    /**
     * 初始化paint
     */
    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        sPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        iPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //绘制颜色
        int colorCount = 12;
        int colorAngleStep = 360 / 12;
        int[] colors = new int[colorCount];
        float[] hsv = new float[]{0f, 1f, 1f};
        for (int i = 0; i < colors.length; i++) {
            hsv[0] = (i * colorAngleStep + 180) % 360;
            colors[i] = Color.HSVToColor(hsv);
        }
        SweepGradient sweepGradient = new SweepGradient(0, 0, COLOR, null);
        RadialGradient radialGradient = new RadialGradient(0, 0, circleRadius, Color.WHITE, 0x00FFFFFF, Shader.TileMode.CLAMP);
        mPaint.setShader(sweepGradient);
        sPaint.setShader(radialGradient);
        iPaint.setStyle(Paint.Style.STROKE);
        iPaint.setStrokeWidth(dp2px(getContext(),1));
        iPaint.setColor(getContext().getResources().getColor(R.color.color_54565A,null));
        innerCirclePaint.setColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*如果没有重写该方法，MeasureSpec.AT_MOST和EXACTLY表现形式一样*/
        int realSize=2*circleRadius+2*innerCircleRadius+2*dp2px(getContext(),1);
        int width;
        int height;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //width
        if (widthMode== MeasureSpec.EXACTLY){
            width=widthSize;
        }
        else if (widthMode== MeasureSpec.AT_MOST){
            width=realSize;
        }
        else{
            width=widthSize;
        }

        //height
        if (heightMode== MeasureSpec.EXACTLY){
            height=heightSize;
        }
        else if (heightMode== MeasureSpec.AT_MOST){
            height=realSize;
        }
        else {
            height = heightSize;
        }
        int min = Math.min(width, height);
        offset = min/2;
        setMeasuredDimension(min,min);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        realRadius = (float) (width-2*innerCircleRadius)/2;
        canvas.translate(offset,offset);
        canvas.drawCircle(0,0,realRadius,mPaint);
        canvas.drawCircle(0,0,realRadius,sPaint);
        if (mIsVisible){
            canvas.drawCircle(centerX,centerY,innerCircleRadius,innerCirclePaint);
            canvas.drawCircle(centerX,centerY,innerCircleRadius,iPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX()-offset;
        int y = (int) event.getY()-offset;
        r = Math.sqrt(x * x + y * y);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                double distance = Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));
                if (distance<innerCircleRadius){
                    //点击圆内圆点位置
                    isMove=true;
                    lastX = x;
                    lastY = y;

                    float[] hsv0={0,0,1};
                    //hsv为逆时针看角度
                    //<0说明处于1，2象限，此时应取反
                    if (Math.atan2(y,x)<0){
                        hsv0[0]= (float) ((-Math.atan2(y,x))/ Math.PI*180f) ;
                    }
                    //>0说明处于3，4象限，此时使用2*PI减去弧度
                    else{
                        hsv0[0]= (float) ((2*Math.PI - Math.atan2(y,x))/ Math.PI*180f) ;

//                        //固件颜色区间实现不了，做范围限制
//                        if (Math.PI/2<Math.atan2(y,x) && Math.atan2(y,x)<Math.PI*3/4){
//                            hsv0[0]= (float) ((2*Math.PI - Math.PI*3/4)/ Math.PI*180f) ;
//                        }
//                        else if(Math.PI/4<Math.atan2(y,x) && Math.atan2(y,x)<=Math.PI/2){
//                            hsv0[0]= (float) ((2*Math.PI - Math.PI*1/4)/ Math.PI*180f) ;
//                        }
//                        else{
//                            hsv0[0]= (float) ((2*Math.PI - Math.atan2(y,x))/ Math.PI*180f) ;
//                        }
                    }

                    hsv0[1]= Math.max(0f, Math.min(1f,(float) (r/realRadius)));
                    int color = Color.HSVToColor(hsv0);
                    innerCirclePaint.setColor(color);
                    invalidate();


                    if (Math.atan2(y,x)<0){
                        hsv0[0]= (float) ((-Math.atan2(y,x))/ Math.PI*180f) ;
                    }
                    //>0说明处于3，4象限，此时使用2*PI减去弧度
                    else{
//                        hsv0[0]= (float) ((2*Math.PI - Math.atan2(y,x))/ Math.PI*180f) ;
                        //固件颜色区间实现不了，做范围限制
                        if (Math.PI/2<Math.atan2(y,x) && Math.atan2(y,x)<Math.PI*3/4){
                            hsv0[0]= (float) ((2*Math.PI - Math.PI*3/4)/ Math.PI*180f) ;
                        }
                        else if(Math.PI/4<Math.atan2(y,x) && Math.atan2(y,x)<=Math.PI/2){
                            hsv0[0]= (float) ((2*Math.PI - Math.PI*1/4)/ Math.PI*180f) ;
                        }
                        else{
                            hsv0[0]= (float) ((2*Math.PI - Math.atan2(y,x))/ Math.PI*180f) ;
                        }
                    }
                    color = Color.HSVToColor(hsv0);
                    red = (color & 0xff0000) >> 16;
                    green = (color & 0x00ff00) >> 8;
                    blue = (color & 0x0000ff);
                    if (onMoveListener!=null){
                        onMoveListener.onMoveStart(red,green,blue);
                    }

                }
                else if(r<realRadius){
                    //点击圆内非圆点位置
                    centerX=x;
                    centerY=y;
                    lastX = x;
                    lastY = y;
                    isMove=true;


                    float[] hsv0={0,0,1};
                    //hsv为逆时针看角度
                    //<0说明处于1，2象限，此时应取反
                    if (Math.atan2(y,x)<0){
                        hsv0[0]= (float) ((-Math.atan2(y,x))/ Math.PI*180f) ;
                    }
                    //>0说明处于3，4象限，此时使用2*PI减去弧度
                    else{
                        hsv0[0]= (float) ((2*Math.PI - Math.atan2(y,x))/ Math.PI*180f) ;
//                        //固件颜色区间实现不了，做范围限制
//                        if (Math.PI/2<Math.atan2(y,x) && Math.atan2(y,x)<Math.PI*3/4){
//                            hsv0[0]= (float) ((2*Math.PI - Math.PI*3/4)/ Math.PI*180f) ;
//                        }
//                        else if(Math.PI/4<Math.atan2(y,x) && Math.atan2(y,x)<=Math.PI/2){
//                            hsv0[0]= (float) ((2*Math.PI - Math.PI*1/4)/ Math.PI*180f) ;
//                        }
//                        else{
//                            hsv0[0]= (float) ((2*Math.PI - Math.atan2(y,x))/ Math.PI*180f) ;
//                        }
                    }

                    hsv0[1]= Math.max(0f, Math.min(1f,(float) (r/realRadius)));
                    int color = Color.HSVToColor(hsv0);
                    innerCirclePaint.setColor(color);
                    invalidate();


                    if (Math.atan2(y,x)<0){
                        hsv0[0]= (float) ((-Math.atan2(y,x))/ Math.PI*180f) ;
                    }
                    //>0说明处于3，4象限，此时使用2*PI减去弧度
                    else{
                        //hsv0[0]= (float) ((2*Math.PI - Math.atan2(y,x))/ Math.PI*180f) ;
                        //固件颜色区间实现不了，做范围限制
                        if (Math.PI/2<Math.atan2(y,x) && Math.atan2(y,x)<Math.PI*3/4){
                            hsv0[0]= (float) ((2*Math.PI - Math.PI*3/4)/ Math.PI*180f) ;
                        }
                        else if(Math.PI/4<Math.atan2(y,x) && Math.atan2(y,x)<=Math.PI/2){
                            hsv0[0]= (float) ((2*Math.PI - Math.PI*1/4)/ Math.PI*180f) ;
                        }
                        else{
                            hsv0[0]= (float) ((2*Math.PI - Math.atan2(y,x))/ Math.PI*180f) ;
                        }
                    }
                    color = Color.HSVToColor(hsv0);
                    red = (color & 0xff0000) >> 16;
                    green = (color & 0x00ff00) >> 8;
                    blue = (color & 0x0000ff);
                    if (onMoveListener!=null){
                        onMoveListener.onMoveStart(red,green,blue);
                    }
                }
                else{

                }



                break;
            case MotionEvent.ACTION_MOVE:
                if (isMove){
                    if (r >realRadius){
                        //滑动半径大于实际半径
                        x*=realRadius/ r;
                        y*=realRadius/ r;
                        centerX=x;
                        centerY=y;
                        lastX=x;
                        lastY=y;
                    }else {
                        //处于圆内
                        offsetX = x - lastX;
                        offsetY = y - lastY;
                        centerX += offsetX;
                        centerY += offsetY;
                        lastX = x;
                        lastY = y;
                    }
                    float[] hsv={0,0,1};
                    //hsv为逆时针看角度

                    Log.i(TAG, "onTouchEvent: "+Math.atan2(y,x));
                    //<0说明处于1，2象限，此时应取反
                    if (Math.atan2(y,x)<0){
                        hsv[0]= (float) ((-Math.atan2(y,x))/ Math.PI*180f) ;
                    }
                    //>0说明处于3，4象限，此时使用2*PI减去弧度
                    else{
                        hsv[0]= (float) ((2*Math.PI - Math.atan2(y,x))/ Math.PI*180f);
//                        //固件颜色区间实现不了，做范围限制
//                        if (Math.PI/2<Math.atan2(y,x) && Math.atan2(y,x)<Math.PI*3/4){
//                            hsv[0]= (float) ((2*Math.PI - Math.PI*3/4)/ Math.PI*180f) ;
//                        }
//                        else if(Math.PI/4<Math.atan2(y,x) && Math.atan2(y,x)<=Math.PI/2){
//                            hsv[0]= (float) ((2*Math.PI - Math.PI*1/4)/ Math.PI*180f) ;
//                        }
//                        else{
//                            hsv[0]= (float) ((2*Math.PI - Math.atan2(y,x))/ Math.PI*180f) ;
//                        }

                    }

                    hsv[1]= Math.max(0f, Math.min(1f,(float) (r/realRadius)));
                    int currentColor = Color.HSVToColor(hsv);
                    innerCirclePaint.setColor(currentColor);
                    invalidate();

                    //<0说明处于1，2象限，此时应取反
                    if (Math.atan2(y,x)<0){
                        hsv[0]= (float) ((-Math.atan2(y,x))/ Math.PI*180f) ;
                    }
                    //>0说明处于3，4象限，此时使用2*PI减去弧度
                    else{
                        //hsv[0]= (float) ((2*Math.PI - Math.atan2(y,x))/ Math.PI*180f);
                        //固件颜色区间实现不了，做范围限制
                        if (Math.PI/2<Math.atan2(y,x) && Math.atan2(y,x)<Math.PI*3/4){
                            hsv[0]= (float) ((2*Math.PI - Math.PI*3/4)/ Math.PI*180f) ;
                        }
                        else if(Math.PI/4<Math.atan2(y,x) && Math.atan2(y,x)<=Math.PI/2){
                            hsv[0]= (float) ((2*Math.PI - Math.PI*1/4)/ Math.PI*180f) ;
                        }
                        else{
                            hsv[0]= (float) ((2*Math.PI - Math.atan2(y,x))/ Math.PI*180f) ;
                        }

                    }
                    currentColor = Color.HSVToColor(hsv);
                    red = (currentColor & 0xff0000) >> 16;
                    green = (currentColor & 0x00ff00) >> 8;
                    blue = (currentColor & 0x0000ff);
                    if (onMoveListener!=null) {
                        onMoveListener.onMove(red, green, blue);
                    }

                    //Log.i(TAG,"red="+ red +" green"+ green +" blue"+ blue);
                }
                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (onMoveListener!=null) {
                            onMoveListener.onMoveUp(red, green, blue);
                        }
                    }
                },200);
                isMove=false;
                break;

            default:
                break;
        }
        return true;
    }

    /**
     * 返回当前rgb的值
     * @return
     */
    public String getRgb(){
        return ColorUtils.rgb2Hex(new int[]{red,green,blue});
    }

    public interface onMoveListener{
        void onMoveStart(int r, int g, int b);
        void onMove(int r, int g, int b);
        void onMoveUp(int r, int g, int b);
    }


    public void setOnMoveListener(onMoveListener listener){
        this.onMoveListener = listener;
    }


    /**
     * 圆点指示器是否可见
     * @param isVisible
     */
    public void setPointVisible(boolean isVisible){
        if (isVisible!=mIsVisible){
            mIsVisible = isVisible;
            invalidate();
        }
    }

    /**
     * 根据rgb确定圆点指示器位置
     */
    public void setPointPosition(int rgb){
        //hsv h:色调（0°~360°） s:饱和度（0~1） v:明度
        float[] hsv = new float[3];
        Color.colorToHSV(rgb,hsv);
        float centerX = 0;
        float centerY = 0;
        float radius = hsv[1] * realRadius;
        //Math.toRadians将角度转换成弧度
        //由于原点移动至控件中心，所以Y轴正负相反
        int pointX = (int) (radius * Math.cos(Math.toRadians(hsv[0])) + centerX);
        int pointY = -(int) (radius * Math.sin(Math.toRadians(hsv[0])) + centerY);
        this.centerX = pointX;
        this.centerY = pointY;
        String hexColor = Integer.toHexString(rgb);
        if (hexColor.equals(BLACK)||hexColor.equalsIgnoreCase(FFBLACK)){
            innerCirclePaint.setColor(ColorUtils.hex2Int("FFFFFF"));
        }
        else{
            innerCirclePaint.setColor(rgb);
        }

        if (hexColor.length() == 8){
            hexColor = hexColor.substring(2);
        }
        int[] colors = ColorUtils.hex2rgb(hexColor);
        red = colors[0];
        green = colors[1];
        blue = colors[2];

        Log.i(TAG, "centerX: "+this.centerX+" centerY:"+this.centerY);
        invalidate();
    }


    /**
     * dp->px
     */
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
