package com.company;

import java.util.*;

import static com.company.FileUtils.*;

public class Compressor {

    private static final int SIZE_ARRAY_FREQUENCY = 255;
    private String fileName;

    private Compressor() {

    }

    public static Compressor instance() {
        return Singleton.INSTANCE.compressor;
    }

    public byte[] compress(String fileName) {
        this.fileName = fileName;
        return compress(readBytes(fileName));
    }

    public byte[] compress(byte[] content) {
        final int[] frequency = calculateFrequency(content);
        final BinaryNode root = HuffmanTree.buildTree(frequency);
        final String binaryString = buildBinaryString(content, root);

        final byte[] result = Splitter.toBytes(binaryString);
        if (fileName != null) {
            writeDataToFile(frequency, content.length, result);
        }
        return result;
    }

    private int[] calculateFrequency(byte[] content) {
        int[] frequency = new int[SIZE_ARRAY_FREQUENCY];
        for (int i = 0; i < content.length; i++) {
            byte letter = content[i];
            frequency[letter]++;
        }
        return frequency;
    }

    private void writeDataToFile(int[] frequency, int sizeContent, byte[] result) {
        Meta.write(fileName + EXT_META, frequency, sizeContent);
        writeBytes(fileName + EXT_COMPRESSED, result);
    }

    private String buildBinaryString(byte[] content, BinaryNode root) {
        final Map<Byte, String> codeTable = buildCodeTable(root);
        final StringBuilder result = new StringBuilder();

        for (int i = 0; i < content.length; i++) {
            if (codeTable.containsKey(content[i])) {
                result.append(codeTable.get(content[i]));
            } else {
                throw new IllegalStateException("Unexpected symbol");
            }
        }
        return result.toString();
    }

    private Map<Byte, String> buildCodeTable(BinaryNode root) {
        final Map<Byte, String> codeTable = new HashMap<>();
        buildCodeTable(root, new StringBuilder(), codeTable);
        return codeTable;
    }

    private void buildCodeTable(BinaryNode node, StringBuilder code, Map<Byte, String> codeTable) {
        if (node == null) {
            return;
        }
        if (node.isLeaf()) {
            if (code.length() == 0) {
                code.append('0');
            }
            codeTable.put(node.getCharacter(), code.toString());
            return;
        }
        buildCodeTable(node.getLeft(), code.append('0'), codeTable);
        code.deleteCharAt(code.length() - 1);
        buildCodeTable(node.getRight(), code.append('1'), codeTable);
        code.deleteCharAt(code.length() - 1);
    }

    private enum Singleton {
        INSTANCE;
        private Compressor compressor;

        Singleton() {
            compressor = new Compressor();
        }
    }
}