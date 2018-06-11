package com.company;

import java.util.ArrayList;
import java.util.List;

import static com.company.FileUtils.*;

public class Decompressor {

    private Decompressor() {

    }

    public static Decompressor instance() {
        return Singleton.INSTANCE.decompressor;
    }

    public byte[] decompress(String fileName) throws UnexpectedFileFormat {
        final byte[] content = readBytes(fileName);
        final int[] frequency = Meta.read(fileName);
        final byte[] decodeContent = decode(content, Meta.getSizeContent(), HuffmanTree.buildTree(frequency));

        if (!isValidFormat(decodeContent)) {
            throw new UnexpectedFileFormat();
        }
        writeBytes(fileName + EXT_DECOMPRESSED, decodeContent);
        return decodeContent;
    }

    private byte[] decode(byte[] content, int sizeSourceContent, BinaryNode node) {
        final List<Byte> byteList = new ArrayList<>();
        final BinaryNode root = node;
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
                    node = root;
                    if (byteList.size() == sizeSourceContent) {
                        break;
                    }
                }
            }
        }
        return Bytes.valueOf(byteList);
    }

    private enum Singleton {
        INSTANCE;
        private Decompressor decompressor;

        Singleton() {
            decompressor = new Decompressor();
        }
    }
}
