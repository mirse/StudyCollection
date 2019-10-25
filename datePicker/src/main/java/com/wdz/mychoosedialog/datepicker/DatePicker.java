package com.wdz.mychoosedialog.datepicker;

import android.content.Context;

public class DatePicker {

    private final CalendarPicker calendarPicker;

    public DatePicker(Context context, CalendarPicker.Callback callback) {
        calendarPicker = new CalendarPicker(context,callback);
    }
    public DatePicker isCancelable(boolean isCancekable){
        calendarPicker.isCancelable = isCancekable;
        return this;
    }
    public DatePicker isScrollLoop(boolean isScrollLoop){
        calendarPicker.isScrollLoop = isScrollLoop;
        return this;
    }
    public DatePicker showTime(String showTime){
        calendarPicker.showTime(showTime);
        return this;
    }
    public DatePicker setTime(long beginTime,long endTime){
        calendarPicker.setTime(beginTime,endTime);
        return this;
    }
    public void create(){
        calendarPicker.show();
    }
}
