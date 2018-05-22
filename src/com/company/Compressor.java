package com.company;

import java.io.IOException;
import java.util.*;

public class Compressor extends CustomFile {

    public void compress(String fileName) throws IOException {
        List<String> strings = super.read(fileName);

        CustomHuffman customHuffman = CustomHuffman.buildTree(strings);
        String result = customHuffman.code(strings.get(0));

        //TODO разбить на биты
        List<String> stringList = new ArrayList<>();
        stringList.add(result);

        super.save(fileName + ".compressed", stringList);
    }

}
