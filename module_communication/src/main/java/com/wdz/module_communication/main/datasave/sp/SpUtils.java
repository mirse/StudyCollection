package com.wdz.module_communication.main.datasave.sp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class SpUtils {

    private final SharedPreferences sp;
    private final SharedPreferences.Editor editor;
    private static final String MESH_UNICAST_ADDR_COUNTER_TEST = "unicast_addr_counter_test";
    public SpUtils(Context context) {
        sp = context.getSharedPreferences("spFiles", Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    /**
     * 保存虚拟地址集合
     * @param primaryAddr
     */
    public void savePrimaryAddress(String primaryAddr) {

        Set<String> mAddrList = getPrimaryAddress();
        mAddrList.add(primaryAddr);

        editor.putStringSet(MESH_UNICAST_ADDR_COUNTER_TEST, mAddrList).apply();

    }

    /**
     * 删除虚拟地址
     * @param primaryAddr
     */
    public void deletePrimaryAddress(String primaryAddr){

        Set<String> mAddrList = getPrimaryAddress();
        if (!mAddrList.isEmpty()&&mAddrList.contains(primaryAddr)){
            mAddrList.remove(primaryAddr);
            editor.putStringSet(MESH_UNICAST_ADDR_COUNTER_TEST, mAddrList).apply();
        }

    }

    /**
     * 获取虚拟地址
     * @return
     */
    public Set<String> getPrimaryAddress() {
        /** address type rule
         * Unassigned address: 0b0000000000000000
         * Unicast address: 0b0xxxxxxxxxxxxxxx, 0x0001~0x7FFF
         * Virtual address: 0b10xxxxxxxxxxxxxx, 0x8000~0xBFFF
         * Group address: 0b11xxxxxxxxxxxxxx, 0xC000~0xFFFF
         */
        return sp.getStringSet(MESH_UNICAST_ADDR_COUNTER_TEST,new HashSet<String>());

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
