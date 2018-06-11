package com.company;

import java.util.ArrayList;
import java.util.List;

public class Splitter {
    public static byte[] toBytes(String inputString) {
        final List<Byte> byteList = new ArrayList<>();
        int numberByte = 0;
        byte buf = 0;

        for (int i = 0; i < inputString.length(); i++) {
            //buf = (byte) (buf | (((inputString.charAt(i) == '1') ? 1 : 0) << (7 - numberByte)));
            int pivot;
            if (inputString.charAt(i) == '1') {
                pivot = 1;
            } else {
                pivot = 0;
            }
            int offset = pivot << (7 - numberByte);
            buf = (byte) (buf | offset);

            numberByte++;
            if ((numberByte == 8) || (i == inputString.length() - 1)) {
                byteList.add(buf);
                buf = 0;
                numberByte = 0;
            }
        }
        return Bytes.valueOf(byteList);
    }
}
