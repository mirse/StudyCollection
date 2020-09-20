package com.wdz.module_customview.main.viewbase;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;


import com.wdz.module_customview.R;

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
    private List<Point> mDatas = new ArrayList<>();
    private List<String> errorList = new ArrayList<>();
    private float lineSpace;
    private float verticalLineSpace;
    private Context mContext;
    private float textHeight;
    /*
     * 图表高度 即屏幕范围可见图表高度 = realHeight 距离底侧边距
     */
    private int lineChartHeight;
    /*
     * 图表宽度 即屏幕范围可见图表宽度 = realWidth -  距离左侧边距
     */
    private int lineChartWidth;
    /**
     * 实际宽度 即控件在屏幕显示的宽度
     */
    private int realWidth;
    /**
     * 实际高度 即控件在屏幕显示的高度
     */
    private int realHeight;

    private int leftMargin = dp2px(getContext(), 32);
    //y轴文字左边距
    private int yTextLeftMargin = dp2px(getContext(), 13);
    //x轴文字底边距
    private int xTextBottomMargin = dp2px(getContext(), 20);
    private int bottomMargin = dp2px(getContext(), 51);
    /**
     * 折线图距离顶部间距
     */
    private int topMargin = dp2px(getContext(), 20);
    private int rightMargin = dp2px(getContext(), 3);

    private int circleRadius = dp2px(getContext(), 3);

    /*
     * x轴显示列数
     */
    private int xCount = 7;
    private Paint polyLinePaint;
    private float[] xPoint;
    private float[] yPoint;
    private Path shadowPath;
    private Path polyLinePath;
    private Paint shadowPaint;
    /**
     * 图标圆点集合
     */
    List<Point> mCircles = new ArrayList<>();
    /**
     * 起点是否画过
     */
    private boolean isStartPoint = false;
    private boolean isErrorPoint = false;

    public LineChartView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * 设置折线图X、Y轴数据源
     * 两个集合为对应关系 以确定折现点位置
     *
     * @param xList X轴数据源 为String类型List
     * @param yList Y轴数据源 为Integer类型List
     */
    public void setXYDataList(List<String> xList, List<Integer> yList) {
        this.xList = xList;
        this.yList = yList;
        invalidate();
    }

    /**
     * 设置折线图数据源
     *
     * @param mDatas
     */
    public void setDataList(List<Point> mDatas) {
        this.mDatas = mDatas;
        invalidate();
    }



    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
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
        textPaint.setTextSize(sp2px(mContext, 12));


        //获取文字高度
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        textHeight = fontMetrics.bottom - fontMetrics.top;

        //折线paint
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStrokeWidth(dp2px(mContext, 2));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(lineColor);

        //折线paint
        polyLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        polyLinePaint.setStrokeWidth(dp2px(mContext, 1));
        polyLinePaint.setStyle(Paint.Style.STROKE);
        polyLinePaint.setColor(getResources().getColor(R.color.color_418FDE));


        //正常圆点 paint
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(circleColor);
        circlePaint.setStrokeWidth(dp2px(mContext, 1));
        circlePaint.setColor(getResources().getColor(R.color.color_418FDE));

        //异常圆点 paint
        errorCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        errorCirclePaint.setColor(errorCircleColor);

        shadowPaint = new Paint();
        shadowPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i=0;i<yList.size();i++){
            Rect rect = new Rect();
            textPaint.getTextBounds(String.valueOf(yList.get(i)), 0,String.valueOf(yList.get(i)).length(), rect);
            int measureWidth = rect.width();
            if (leftMargin<measureWidth){
                leftMargin = measureWidth;
            }
        }

        //折线图表高度
        lineChartHeight = realHeight - bottomMargin;
        lineChartWidth = realWidth - leftMargin;
        lineSpace = (float) (lineChartHeight -topMargin) / (yList.get(yList.size()-1));
        verticalLineSpace = (float) lineChartWidth / xCount;
        canvas.drawColor(getResources().getColor(R.color.white));
        drawChart(canvas);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);   //获取宽的模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec); //获取高的模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);   //获取宽的尺寸
        int heightSize = MeasureSpec.getSize(heightMeasureSpec); //获取高的尺寸
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
        Log.i(TAG, "onMeasure: " + xList.size());
        //保存测量宽度和测量高度
        if (xList.size() > 6) {
            setMeasuredDimension(realWidth + (xList.size() - 6) * (realWidth / 6), realHeight);
        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(TAG, "onLayout: left:" + left + " top:" + top + " right:" + right + " bottom:" + bottom);
        Log.i(TAG, "onLayout getWidth: " + getWidth());

    }

    /**
     * 绘制x轴线
     *
     * @param canvas
     */

    private void drawChart(Canvas canvas) {
        drawShadow(canvas);
        drawXLine(canvas);
        drawYLine(canvas);
        drawPolyLine(canvas);
        drawCircle(canvas);

    }

    /**
     * 绘制X轴方向线及对应Y轴刻度值
     *
     * @param canvas
     */
    private void drawXLine(Canvas canvas) {
        Log.i(TAG, "drawXLine: " + lineSpace + "lineChartHeight:" + lineChartHeight+" leftMargin:"+leftMargin);
        for (int i = 0; i < yList.size(); i++) {
            if (i == 0) {
                //i=0 即第一条x轴线，为实线
                canvas.drawLine(leftMargin, lineChartHeight - lineSpace * i, getWidth() - rightMargin, lineChartHeight - lineSpace * i, linePaint);
            } else {
                linePaint.setStrokeWidth(dp2px(mContext, 1));
                linePaint.setPathEffect(new DashPathEffect(new float[]{5, 2}, 0));
                //canvas.drawLine绘画不了虚线
                Path linePath = new Path();
                linePath.moveTo(leftMargin, lineChartHeight - lineSpace * yList.get(i));
                linePath.lineTo(getWidth() - rightMargin, lineChartHeight - lineSpace * yList.get(i));
                canvas.drawPath(linePath, linePaint);
            }
            //绘制y轴对应文字
            canvas.drawText(String.valueOf(yList.get(i)), (float) yTextLeftMargin/2, lineChartHeight - lineSpace * yList.get(i) + textHeight / 3, textPaint);

        }
    }

    /**
     * 绘制Y轴方向线及对应X轴刻度值
     *
     * @param canvas
     */
    private void drawYLine(Canvas canvas) {
        Log.i(TAG, "verticalLineSpace: " + verticalLineSpace);
        for (int y = 0; y < xList.size(); y++) {
            //绘制x轴对应文字
            canvas.drawText(String.valueOf(xList.get(y)), leftMargin + verticalLineSpace * y, realHeight - xTextBottomMargin, textPaint);
        }
    }


    /**
     * 画正常坐标点
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        //画圆
        for (int i = 0; i < mDatas.size(); i++) {
            Point currentCircle = mDatas.get(i);
            int x = 0;
            float power = 0;
            //日期
            x = xList.indexOf(currentCircle.xValue);
            //电量
            power = Float.parseFloat(currentCircle.yValue);


            Rect rect = new Rect();
            textPaint.getTextBounds(currentCircle.xValue, 0, currentCircle.xValue.length(), rect);
            int measureWidth = rect.width();
            float textWidthHalf = (float) measureWidth / 2;

            if (currentCircle.isErrorPoint){
                circlePaint.setStyle(Paint.Style.FILL);
                circlePaint.setColor(getResources().getColor(R.color.red_deep));
                canvas.drawCircle(leftMargin + verticalLineSpace * x + textWidthHalf, lineChartHeight - lineSpace * power, circleRadius, circlePaint);
            }
            else{
                circlePaint.setStyle(Paint.Style.FILL);
                circlePaint.setColor(getResources().getColor(R.color.white));
                canvas.drawCircle(leftMargin + verticalLineSpace * x + textWidthHalf, lineChartHeight - lineSpace * power, circleRadius, circlePaint);
                circlePaint.setStyle(Paint.Style.STROKE);
                circlePaint.setColor(getResources().getColor(R.color.color_418FDE));
                canvas.drawCircle(leftMargin + verticalLineSpace * x + textWidthHalf, lineChartHeight - lineSpace * power, circleRadius, circlePaint);
            }

        }
    }




    /**
     * 画折线
     *
     * @param canvas
     */
    private void drawPolyLine(Canvas canvas) {
        isStartPoint = false;
        polyLinePath = new Path();
        //mCircles.addAll(mDatas);
        for (int i = 0; i < mCircles.size(); i++) {
            Point point = mCircles.get(i);
            int x = 0;
            float power = 0;
            //当前圆点
            //日期
            x = xList.indexOf(point.xValue);
            //电量
            power = Float.parseFloat(point.yValue);
            Rect rect = new Rect();
            textPaint.getTextBounds(point.xValue, 0, point.xValue.length(), rect);
            int measureWidth = rect.width();
            float textWidthHalf = (float) measureWidth / 2;

            if (!point.isErrorPoint && !isStartPoint){
                polyLinePath.moveTo(leftMargin + verticalLineSpace * x + textWidthHalf, lineChartHeight - lineSpace * power);
                isStartPoint = true;
            }
            else {
                if (!point.isErrorPoint){
                    if (isErrorPoint){
                        polyLinePath.moveTo(leftMargin + verticalLineSpace * x + textWidthHalf, lineChartHeight - lineSpace * power);
                        isErrorPoint = false;
                    }
                    else{
                        polyLinePath.lineTo(leftMargin + verticalLineSpace * x + textWidthHalf, lineChartHeight - lineSpace * power);
                    }

                }
                else {
                    isErrorPoint = true;
                }
            }

        }
        canvas.drawPath(polyLinePath, polyLinePaint);
    }

    /**
     * 画折线下方阴影部分
     *
     * @param canvas
     */
    private void drawShadow(Canvas canvas) {
        shadowPath = new Path();
        int maxHeightPoint = 0;
        float maxHeight = 0;
        mCircles.addAll(mDatas);
        xPoint = new float[mCircles.size()];
        yPoint = new float[mCircles.size()];
        Log.i(TAG, "drawPolyLine: " + mCircles);
        for (int i = 0; i < mCircles.size(); i++) {
            Point currentCircle = mCircles.get(i);
            int x = 0;
            float power = 0;
            //当前圆点
            //日期
            x = xList.indexOf(currentCircle.xValue);
            //电量
            power = Float.parseFloat(currentCircle.yValue);
            Rect rect = new Rect();
            textPaint.getTextBounds(currentCircle.xValue, 0, currentCircle.xValue.length(), rect);
            int measureWidth = rect.width();
            float textWidthHalf = (float) measureWidth / 2;
            float lastTextWidthHalf;
            //当是第二个点时，需要画连接线
            if (i > 0) {
                shadowPath.lineTo(leftMargin + verticalLineSpace * x + textWidthHalf, lineChartHeight - lineSpace * power);
                xPoint[i] = leftMargin + verticalLineSpace * x + textWidthHalf;
                yPoint[i] = lineChartHeight - lineSpace * power;
                if (maxHeight < yPoint[i]) {
                    maxHeight = yPoint[i];
                    maxHeightPoint = i;
                }

            } else {
                shadowPath.moveTo(leftMargin + verticalLineSpace * x + textWidthHalf, lineChartHeight - lineSpace * power);
                xPoint[i] = leftMargin + verticalLineSpace * x + textWidthHalf;
                yPoint[i] = lineChartHeight - lineSpace * power;
                maxHeight = yPoint[i];
            }

        }

        if (maxHeight == yPoint[0] && maxHeight != lineChartHeight) {
            maxHeight += dp2px(getContext(), 30);
        }
        shadowPath.lineTo(xPoint[xPoint.length - 1], maxHeight);
        shadowPath.lineTo(xPoint[0], maxHeight);
        shadowPath.close();

        //绘制阴影
        Shader lShader = new LinearGradient(0, 0, 0, maxHeight, Color.parseColor("#418FDE"), Color.parseColor("#FFFFFF"), Shader.TileMode.REPEAT);
        shadowPaint.setShader(lShader);
        canvas.drawPath(shadowPath, shadowPaint);
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
     *
     * @param context
     * @param spValue
     * @return
     */
    public int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    /**
     * 折线图的点类
     * 包含x值、y值、是否是异常点
     */
    public static class Point {
        public String xValue;
        public String yValue;
        public boolean isErrorPoint;

        public Point() {

        }

        public Point(String xValue, String yValue) {
            this.xValue = xValue;
            this.yValue = yValue;
        }

        public Point(String xValue, String yValue, boolean isErrorPoint) {
            this.xValue = xValue;
            this.yValue = yValue;
            this.isErrorPoint = isErrorPoint;
        }

        public String getxValue() {
            return xValue;
        }

        public void setxValue(String xValue) {
            this.xValue = xValue;
        }

        public String getyValue() {
            return yValue;
        }

        public void setyValue(String yValue) {
            this.yValue = yValue;
        }

        public boolean isErrorPoint() {
            return isErrorPoint;
        }

        public void setErrorPoint(boolean errorPoint) {
            isErrorPoint = errorPoint;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "xValue='" + xValue + '\'' +
                    ", yValue='" + yValue + '\'' +
                    ", isErrorPoint=" + isErrorPoint +
                    '}';
        }
    }


}
