package com.company;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

public class Compressor extends CustomFile {

    public void compress(String fileName) throws IOException {
        List<String> strings = super.read(fileName);

        CustomHuffman customHuffman = CustomHuffman.buildTree(strings);
        String codeString = customHuffman.code(getStringByList(strings));

        saveRootTree(fileName , customHuffman);
        super.save(fileName + EXT_COMPRESSED, parseToByte(codeString));
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
            if ((count == 8) || (i == inputString.length() - 1)) {
                result += buf;
                buf = '\u0000';
                count = 0;
            }
        }
        stringList.add(result);
        return stringList;
    }

    /**
     * Правильней было бы придумать другой алгоритм сохранения дерева в файл
     */
    private void saveRootTree(String fileName, CustomHuffman customHuffman) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fileName + EXT_TREE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(customHuffman.getRoot());
    }
}