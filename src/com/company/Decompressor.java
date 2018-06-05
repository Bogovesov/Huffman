package com.company;

import static com.company.FileUtils.*;

public class Decompressor {

    private Decompressor() {

    }

    public static Decompressor instance() {
        return Singelton.INSTANCE.decompressor;
    }

    public void decompress(String fileName) throws UnexpectedFileFormat {
        final byte[] content = readBytes(fileName);
        final byte[] decodeContent = HuffmanTree.buildTree(Meta.read(fileName)).decode(content);
        if (!isValidFormat(decodeContent)) {
            throw new UnexpectedFileFormat();
        }
        writeBytes(fileName + EXT_DECOMPRESSED, decodeContent);
    }

    private enum Singelton {
        INSTANCE;
        private Decompressor decompressor;

        Singelton() {
            decompressor = new Decompressor();
        }
    }
}
