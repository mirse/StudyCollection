package com.wdz.common.util;

public class ByteUtil {
    public static String byte2HexString(byte[] data){
        StringBuffer stringBuffer = new StringBuffer(data.length);
        String sTemp;
        for (int i = 0; i < data.length; i++) {
            sTemp = Integer.toHexString(0xFF & data[i]);
            if (sTemp.length() < 2){
                stringBuffer.append(0);
            }
            stringBuffer.append(sTemp.toUpperCase());
        }
        return stringBuffer.toString();
    }
}
