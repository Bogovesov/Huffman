package com.company;

import java.io.IOException;
import java.util.*;

import static com.company.FileUtils.*;

public class Compressor {

    private Compressor() {

    }

    public static Compressor instance() {
        return Singelton.INSTANCE.compressor;
    }

    public void compress(String fileName) {
        final List<String> inputString = read(fileName);
        if (inputString.size() > 0) {
            final HuffmanTree huffmanTree = HuffmanTree.buildTree(inputString);
            final String codeString = huffmanTree.code(convertListToString(inputString));

            saveMetaData(fileName, huffmanTree);
            save(fileName + EXT_COMPRESSED, parseToByte(codeString));
        }
    }

    /**
     * Разбиение закодированной строки побайтно
     */
    private String parseToByte(String inputString) {
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
        return result;
    }

    private enum Singelton {
        INSTANCE;
        private Compressor compressor;

        Singelton() {
            compressor = new Compressor();
        }
    }
}