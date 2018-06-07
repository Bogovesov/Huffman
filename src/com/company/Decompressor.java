package com.company;

import java.rmi.MarshalException;
import java.util.ArrayList;
import java.util.List;

import static com.company.FileUtils.*;

public class Decompressor {

    private String fileName;

    private Decompressor() {

    }

    public static Decompressor instance() {
        return Singelton.INSTANCE.decompressor;
    }

    public byte[] decompress(String fileName) throws UnexpectedFileFormat {
        this.fileName = fileName;
        return decompress(readBytes(fileName));
    }

    public byte[] decompress(byte[] content) throws UnexpectedFileFormat {
        int[] frequency = Meta.read(fileName);
        HuffmanTree huffmanTree = HuffmanTree.buildTree(frequency);
        final byte[] decodeContent = decode(content, Meta.getSizeContent(), huffmanTree);

        if (!isValidFormat(decodeContent)) {
            throw new UnexpectedFileFormat();
        }
        writeBytes(fileName + EXT_DECOMPRESSED, decodeContent);
        return decodeContent;
    }

    public byte[] decode(byte[] content, int sizeSourceContent, HuffmanTree huffmanTree) {
        List<Byte> byteList = new ArrayList<>();
        HuffmanTree.Node node = huffmanTree.getRoot();
        char buf;

        for (int i = 0; i < content.length; i++) {
            buf = (char) content[i];
            for (int j = 0; j < 8; j++) {
                if ((buf & (1 << (7 - j))) != 0) {
                    if (node.getRight() != null) {
                        node = node.getRight();
                    }
                } else {
                    if (node.getLeft() != null) {
                        node = node.getLeft();
                    }
                }
                if (node.isLeaf()) {
                    byteList.add(node.getCharacter());
                    node = huffmanTree.getRoot();
                    if (byteList.size() == sizeSourceContent) {
                        break;
                    }
                }
            }
        }
        return Bytes.valueOf(byteList);
    }

    private enum Singelton {
        INSTANCE;
        private Decompressor decompressor;

        Singelton() {
            decompressor = new Decompressor();
        }
    }
}
