package com.company;

import java.util.List;

public class Bytes {
    public static byte[] valueOf(List<Byte> byteList){
        byte[] byteResult = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            byteResult[i] = byteList.get(i);
        }
        return byteResult;
    }
}
