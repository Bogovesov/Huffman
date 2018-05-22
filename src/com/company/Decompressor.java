package com.company;

import java.io.IOException;
import java.util.List;

public class Decompressor extends CustomFile {

    public void decompress(String fileName) throws IOException {
        List<String> strings = super.read(fileName);

        super.save(fileName + ".decompressed", strings);
    }
}
