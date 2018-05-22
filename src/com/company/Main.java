package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            doCompressDecompress("d:" + File.separator + "1.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void doCompressDecompress(String fileName, boolean compression) throws IOException {
        if (compression) {
            Compressor compressor = new Compressor();
            compressor.compress(fileName);
        } else {
            Decompressor decompressor = new Decompressor();
            decompressor.decompress(fileName);
        }
    }
}
