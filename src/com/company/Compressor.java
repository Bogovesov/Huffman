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
        final String binaryString = getBynaryString(content, huffmanTree);

        final byte[] result = Splitter.toBytes(binaryString);
        if (fileName != null) {
            writeDataToFile(huffmanTree, result);
        }
        return result;
    }

    private void writeDataToFile(HuffmanTree huffmanTree, byte[] result) {
        Meta.write(fileName + EXT_META, huffmanTree.getFrequency(), huffmanTree.getSizeContent());
        writeBytes(fileName + EXT_COMPRESSED, result);
    }

    public String getBynaryString(byte[] content, HuffmanTree huffmanTree) {
        Map<Byte, String> codeTable = codeTable(huffmanTree);
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < content.length; i++) {
            if (codeTable.containsKey(content[i])) {
                result.append(codeTable.get(content[i]));
            }
        }
        return result.toString();
    }

    private Map<Byte, String> codeTable(HuffmanTree huffmanTree) {
        Map<Byte, String> mapCodeTable = new HashMap();
        codeTable(huffmanTree.getRoot(), new StringBuilder(), mapCodeTable);
        return mapCodeTable;
    }

    private void codeTable(HuffmanTree.Node node, StringBuilder code, Map<Byte, String> codeTable) {
        if (node.isLeaf()) {
            if (code.toString().isEmpty()) {
                code.append('0');
            }
            codeTable.put(node.getCharacter(), code.toString());
            return;
        }
        codeTable(node.getLeft(), code.append('0'), codeTable);
        code.deleteCharAt(code.length() - 1);
        codeTable(node.getRight(), code.append('1'), codeTable);
        code.deleteCharAt(code.length() - 1);
    }

    private enum Singelton {
        INSTANCE;
        private Compressor compressor;

        Singelton() {
            compressor = new Compressor();
        }
    }
}