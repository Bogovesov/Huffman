package com.company;

import java.util.*;

import static com.company.FileUtils.*;

public class Compressor {

    private String fileName;

    private Compressor() {

    }

    public static Compressor instance() {
        return Singelton.INSTANCE.compressor;
    }

    public byte[] compress(String fileName) {
        this.fileName = fileName;
        return compress(readBytes(fileName));
    }

    public byte[] compress(byte[] content) {
        final HuffmanTree huffmanTree = HuffmanTree.buildTree(content);
        final String binaryString = huffmanTree.code(content);

        final byte[] result = splitStringToByte(binaryString);
        if (fileName != null) {
            writeDataToFile(huffmanTree, result);
        }
        return result;
    }

    private void writeDataToFile(HuffmanTree huffmanTree, byte[] result) {
        Meta.write(fileName + EXT_META, huffmanTree.getFrequency());
        writeBytes(fileName + EXT_COMPRESSED, result);
    }

    public byte[] splitStringToByte(String inputString) {
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