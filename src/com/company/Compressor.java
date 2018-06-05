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
        final byte[] content = readBytes(fileName);
        final HuffmanTree huffmanTree = HuffmanTree.buildTree(content);
        final String binaryString = huffmanTree.code(content);

        Meta.write(fileName + EXT_META, huffmanTree.getFrequency());
        writeBytes(fileName + EXT_COMPRESSED, splitStringToByte(binaryString));
    }

    private byte[] splitStringToByte(String inputString) {
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

    private enum Singelton {
        INSTANCE;
        private Compressor compressor;

        Singelton() {
            compressor = new Compressor();
        }
    }
}