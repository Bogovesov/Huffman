package com.company;

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
        final byte[] decodeContent = HuffmanTree.buildTree(Meta.read(fileName)).decode(content);
        if (!isValidFormat(decodeContent)) {
            throw new UnexpectedFileFormat();
        }
        writeBytes(fileName + EXT_DECOMPRESSED, decodeContent);
        return decodeContent;
    }

    private enum Singelton {
        INSTANCE;
        private Decompressor decompressor;

        Singelton() {
            decompressor = new Decompressor();
        }
    }
}
