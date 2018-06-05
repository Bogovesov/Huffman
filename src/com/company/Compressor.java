package com.company;

import java.util.*;

import static com.company.FileUtils.*;

public class Compressor {

    private Compressor() {

    }

    public static Compressor instance() {
        return Singelton.INSTANCE.compressor;
    }

    public void compress(String fileName) {
        final byte[] content = read(fileName);
        final HuffmanTree huffmanTree = HuffmanTree.buildTree(content);
        final String codeString = huffmanTree.code(content);

        saveMetaData(fileName, huffmanTree);
        save(fileName + EXT_COMPRESSED, parseToByte(codeString));
//        parseToByte(fileName + EXT_COMPRESSED, codeString);
    }

    /**
     * Разбиение закодированной строки побайтно
     */
    private byte[] parseToByte(String inputString) {
        int count = 0;
        int buf = 0;
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < inputString.length(); i++) {
            buf = (buf | (((inputString.charAt(i) == '1') ? 1 : 0) << (7 - count)));
            count++;
            if ((count == 8) || (i == inputString.length() - 1)) {
                result.append((char)buf);
                buf = 0;
                count = 0;
            }
        }
        return result.toString().getBytes();
    }

    private enum Singelton {
        INSTANCE;
        private Compressor compressor;

        Singelton() {
            compressor = new Compressor();
        }
    }
}