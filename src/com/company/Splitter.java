package com.company;

import java.util.ArrayList;
import java.util.List;

public class Splitter {
    public static byte[] toBytes(String inputString) {
        final List<Byte> byteList = new ArrayList<>();
        int count = 0;
        byte buf = 0;

        for (int i = 0; i < inputString.length(); i++) {
            //buf = (byte) (buf | (((inputString.charAt(i) == '1') ? 1 : 0) << (7 - count)));
            int pivot;
            if (inputString.charAt(i) == '1') {
                pivot = 1;
            } else {
                pivot = 0;
            }
            int offset = pivot << (7 - count);
            buf = (byte) (buf | offset);

            count++;
            if ((count == 8) || (i == inputString.length() - 1)) {
                byteList.add(buf);
                buf = 0;
                count = 0;
            }
        }
        return Bytes.valueOf(byteList);
    }
}
