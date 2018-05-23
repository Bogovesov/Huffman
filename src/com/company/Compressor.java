package com.company;

import java.io.IOException;
import java.util.*;

public class Compressor extends CustomFile {

    public void compress(String fileName) throws IOException {
        List<String> strings = super.read(fileName);
        String codeString = CustomHuffman.buildTree(strings).code(getStringByList(strings));
        super.save(fileName + ".compressed", parseToByte(codeString));
    }

    /**
     * Разбиение закодированной строки побайтно
     */
    private List<String> parseToByte(String inputString) {
        List<String> stringList = new ArrayList<>();

        int count = 0;
        char buf = '\u0000';
        String result = "";

        for (int i = 0; i < inputString.length(); i++) {
            buf = (char) (buf | (((inputString.charAt(i) == '1') ? 1 : 0) << (7 - count)));
            count++;
            if (count == 8) {
                result += buf;
                buf = '\u0000';
                count = 0;
            }
        }
        stringList.add(result);
        return stringList;
    }

    private String getStringByList(List<String> strings) {
        String resultStr = "";
        for (String string : strings) {
            resultStr += string;
        }
        return resultStr;
    }
}