package com.company;

import java.util.ArrayList;
import java.util.List;

public class Spliter {
    public static byte[] splitStringToByte(String inputString) {
        final List<Byte> byteList = new ArrayList<>();

        int count = 0;
        char buf = 0;

        for (int i = 0; i < inputString.length(); i++) {
            buf = (char) (buf | (((inputString.charAt(i) == '1') ? 1 : 0) << (7 - count)));
            count++;
            if ((count == 8) || (i == inputString.length() - 1)) {
                byteList.add((byte) buf);
                buf = 0;
                count = 0;
            }
        }
        return Bytes.valueOf(byteList);
    }
}
