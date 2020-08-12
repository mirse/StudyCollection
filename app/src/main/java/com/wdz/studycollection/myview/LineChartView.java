package com.wdz.studycollection.myview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.wdz.studycollection.R;
import com.wdz.studycollection.handler.model.Login;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dezhi.wang
 */
public class LineChartView extends View {
    private final String TAG = this.getClass().getSimpleName();
    private int textColor;
    private int lineColor;
    private int circleColor;
    private int errorCircleColor;
    private Paint textPaint;
    private Paint linePaint;
    private Paint circlePaint;
    private Paint errorCirclePaint;
    private List<Integer> yList = new ArrayList<>();
    private List<String> xList = new ArrayList<>();
    private List<String> mDatas = new ArrayList<>();
    private float lineSpace = (float) getHeight()/(yList.size()-1);
    private float verticalLineSpace = (float) getWidth()/(xList.size()-1);
    private Context mContext;
    private float textHeight;
    //
    private int lineChartHeight;
    private int lineChartWidth;
    /**
     * 实际宽度 即控件在屏幕显示的宽度
     */
    private int realWidth;
    /**
     * 实际高度 即控件在屏幕显示的高度
     */
    private int realHeight;
    private Paint polyLinePaint;
    private float dataSpace;

    public LineChartView(Context context) {
        super(context);
        init(context,null,0);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    /**
     * 设置折线图X、Y轴数据源
     * 两个集合为对应关系 以确定折现点位置
     * @param xList X轴数据源 为String类型List
     * @param yList Y轴数据源 为Integer类型List
     */
    public void setXYDataList(List<String> xList,List<Integer> yList){
        this.xList = xList;
        this.yList = yList;
        invalidate();
    }

    /**
     * 设置折线图数据源
     * @param mDatas
     */
    public void setDataList(List<String> mDatas){
        this.mDatas = mDatas;
        invalidate();
    }


    private void init(Context context,AttributeSet attrs, int defStyleAttr) {
        mContext = context;
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LineChartView, defStyleAttr, 0);
        textColor = typedArray.getColor(R.styleable.LineChartView_textColor, getResources().getColor(R.color.black_deep));
        lineColor = typedArray.getColor(R.styleable.LineChartView_lineColor, getResources().getColor(R.color.grey_d));
        circleColor = typedArray.getColor(R.styleable.LineChartView_circleColor, getResources().getColor(R.color.black_deep));
        errorCircleColor = typedArray.getColor(R.styleable.LineChartView_errorCircleColor, getResources().getColor(R.color.grey_d));
        typedArray.recycle();

        setLayerType(LAYER_TYPE_SOFTWARE, null);
        initPaint();


    }

    /**
     * 初始化paint
     */
    private void initPaint() {
        //文字paint x轴文字、y轴文字、虚线绘制
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(sp2px(mContext,12));


        //获取文字高度
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        textHeight = fontMetrics.bottom - fontMetrics.top;
        //折线paint
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStrokeWidth(dp2px(mContext,2));
        linePaint.setStyle (Paint.Style.STROKE) ;
        linePaint.setColor(lineColor);

        //折线paint
        polyLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        polyLinePaint.setStrokeWidth(dp2px(mContext,2));
        polyLinePaint.setStyle (Paint.Style.FILL) ;
        polyLinePaint.setColor(getResources().getColor(R.color.color_418FDE));
        polyLinePaint.setShadowLayer(20, 10,10, R.drawable.line_chart_shadow);




        //正常圆点 paint
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(circleColor);
        circlePaint.setStrokeWidth(dp2px(mContext,1));
        circlePaint.setStrokeCap(Paint.Cap.ROUND);
        circlePaint.setStyle(Paint.Style.STROKE);

        //异常圆点 paint
        errorCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        errorCirclePaint.setColor(errorCircleColor);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        lineChartHeight = realHeight-sp2px(getContext(),12)-dp2px(getContext(),20);
        lineChartWidth = realWidth-sp2px(getContext(),31)-dp2px(getContext(),1);
        lineSpace = (float) lineChartHeight/(yList.size());
        dataSpace = (float) lineChartHeight/(yList.get(yList.get(yList.size()-1))*10);

        Log.i(TAG, "onDraw: dataSpace:"+dataSpace);
        verticalLineSpace =  (float) lineChartWidth/6;
        canvas.drawColor(getResources().getColor(R.color.white));
        drawXLine(canvas);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);   //获取宽的模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec); //获取高的模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);   //获取宽的尺寸
        int heightSize = MeasureSpec.getSize(heightMeasureSpec); //获取高的尺寸
        Log.v(TAG, "宽的模式:"+widthMode);
        Log.v(TAG, "高的模式:"+heightMode);
        Log.v(TAG, "宽的尺寸:"+widthSize);
        Log.v(TAG, "高的尺寸:"+heightSize);
        if (widthMode == MeasureSpec.EXACTLY) {
            //如果match_parent或者具体的值，直接赋值
            realWidth = widthSize;
        } else {
            realWidth = widthSize;
        }
        //高度跟宽度处理方式一样
        if (heightMode == MeasureSpec.EXACTLY) {
            realHeight = heightSize;
        } else {
            realHeight = heightSize;
        }
        Log.i(TAG, "onMeasure: "+xList.size());
        //保存测量宽度和测量高度
        if (xList.size()>6){
            setMeasuredDimension(realWidth+(xList.size()-6)*(realWidth/6), realHeight);
        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(TAG, "onLayout: left:"+left+" top:"+top+" right:"+right+" bottom:"+bottom);
        Log.i(TAG, "onLayout getWidth: "+getWidth());

    }

    /**
     * 绘制x轴线
     * @param canvas
     */
    private void drawXLine(Canvas canvas) {
        Log.i(TAG, "drawXLine: "+lineSpace+"lineChartHeight:"+lineChartHeight);
        for (int i=0;i<yList.size();i++){
            Log.i(TAG, "String.valueOf(yList.get(i)): "+String.valueOf(yList.get(i)));

            if (i==0){
                canvas.drawLine(dp2px(mContext,32),lineChartHeight-lineSpace*i,getWidth()-dp2px(mContext,5),lineChartHeight-lineSpace*i,linePaint);
            }
            else {
                linePaint.setStrokeWidth(dp2px(mContext,1));
                linePaint.setPathEffect(new DashPathEffect(new float[]{5,2},0));
                //canvas.drawLine绘画不了虚线
                Path linePath = new Path();
                linePath.moveTo(dp2px(mContext,32),lineChartHeight-lineSpace*i);
                linePath.lineTo(getWidth()-dp2px(mContext,5),lineChartHeight-lineSpace*i);
                canvas.drawPath(linePath,linePaint);
            }
            //绘制x轴对应文字
            canvas.drawText(String.valueOf(yList.get(i)),dp2px(mContext,13),lineChartHeight-lineSpace*i+textHeight/3,textPaint);

        }


        Log.i(TAG, "verticalLineSpace: "+verticalLineSpace);
        for (int y=0;y<xList.size();y++){
            //绘制y轴对应文字
            canvas.drawText(String.valueOf(xList.get(y)),dp2px(mContext,30)+verticalLineSpace*y,getHeight(),textPaint);






        }

        for (int i=0;i<mDatas.size();i++){
            String s = mDatas.get(i);
            String[] split = s.split(",");
            if (split.length==2){
                //日期
                String day = split[0];
                //电量
                String power = split[1];
                int y = yList.indexOf(Integer.parseInt(day));
                float powerFloat = Float.parseFloat(power);

                canvas.drawCircle(dp2px(mContext,30)+verticalLineSpace*y,lineChartHeight-dataSpace*powerFloat,dp2px(mContext,3),circlePaint);

//                if (i>0){
//                    canvas.drawLine(dp2px(mContext,30)+verticalLineSpace*(y-1),lineChartHeight-lineSpace*(y-1),dp2px(mContext,30)+verticalLineSpace*y,lineChartHeight-lineSpace*y,polyLinePaint);
//                }
            }




        }
    }



    /**
     * dp->px
     */
    public int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * sp -> px
     * @param context
     * @param spValue
     * @return
     */
    public int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
