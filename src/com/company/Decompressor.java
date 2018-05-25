package com.company;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Decompressor extends CustomFile {

    public void decompress(String fileName) throws IOException, ClassNotFoundException {
        List<String> stringCompressed = super.read(fileName);

        String fileNameSource = fileName.replaceAll(EXT_COMPRESSED, "");
        String decodeString = CustomHuffman.buildTree(fileNameSource + EXT_TREE).decode(getStringByList(stringCompressed));

        super.save(fileName + EXT_DECOMPRESSED, decodeString);
    }
}
