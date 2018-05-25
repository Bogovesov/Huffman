package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Decompressor extends CustomFile {

    public void decompress(String sourceFile, String fileName) throws IOException {
        List<String> stringsSource = super.read(sourceFile);
        List<String> stringCompressed = super.read(fileName);

        String decodeString = CustomHuffman.buildTree(stringsSource).decode(getStringByList(stringCompressed),
                getStringByList(stringsSource).length());

        System.out.println(decodeString);

        List<String> stringList = new ArrayList<>();
        stringList.add(decodeString);

        super.save(fileName + ".decompressed", stringList);
    }
}
