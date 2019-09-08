package com.example.dezhiwang.studycollection.datasave;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtils {

    private final SharedPreferences sp;
    private final SharedPreferences.Editor editor;

    public SpUtils(Context context) {
        sp = context.getSharedPreferences("spFiles", Context.MODE_PRIVATE);
        editor = sp.edit();
    }
    /*保存键值对*/
    public void saveValue(String key,Object object){
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.apply();
    }

    public Object getValue(String key,Object defaultObject){
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        } else {
            return sp.getString(key, null);
        }
    }


}
