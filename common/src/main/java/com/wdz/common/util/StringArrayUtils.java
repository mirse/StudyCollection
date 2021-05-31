package com.wdz.common.util;

import android.content.Context;

import com.wdz.common.R;

/**
 * @description:
 * @author: dezhi.wang
 * @date :   2021/5/31 9:02
 */
public class StringArrayUtils {
    // 单例
    private volatile static StringArrayUtils mInstance = null;
    private String[] homeTabArray;

    // 类不加synchronized，是为了提高运行效率，不需要每次都加锁
    public static StringArrayUtils getInstance() {
        // mInstance添加了volatile修饰符，保证有序性，不会出现mInstance已经有值却还未完成对象初始化的bug
        if (mInstance == null) {
            // 加锁保证多线程访问时操作的原子性
            synchronized (StringArrayUtils.class) {
                // 线程1已经完成释放锁，线程2来获取时需要再判断一次，避免new出不同对象
                if (mInstance == null) {
                    // cpu可能会进行不影响单线程结果的指令重排序，可能出现先赋值给mInstance，再初始化对象的
                    // 情况，添加volatile修饰符就是为了保证mInstance有值时，初始化肯定已经完成
                    mInstance = new StringArrayUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化StringArray数据相关
     * @param context
     */
    public void init(Context context){
        homeTabArray = context.getResources().getStringArray(R.array.home_tab);
    }

    /**
     * 根据指定位置获取对应value值
     * @param values
     * @param index
     * @return
     */
    private String getValue(String[] values, int index){
        if (values == null || index < 0 || index >= values.length) {
            return "";
        }
        return values[index];
    }

    /*---------------------------------------------------------------------------------------*/


    /**
     * 获取tabValue值
     * @param index
     * @return
     */
    public String getHomeTabValue(int index){
        return getValue(homeTabArray, index);
    }
}
