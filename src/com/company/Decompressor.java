package com.company;

import java.util.List;

import static com.company.FileUtils.*;

public class Decompressor {

    private Decompressor() {

    }

    public static Decompressor instance() {
        return Singelton.INSTANCE.decompressor;
    }

    public void decompress(String fileName) throws UnexpectedFileFormat {
        final byte[] content = read(fileName);

        final String fileNameSource = fileName.replaceAll(EXT_COMPRESSED, "");
        final String decodeString = HuffmanTree.buildTree(fileNameSource + EXT_META).decode(content);

        if (!isValidFormat(decodeString)) {
            throw new UnexpectedFileFormat();
        }
        save(fileName + EXT_DECOMPRESSED, decodeString);
    }

    private enum Singelton {
        INSTANCE;
        private Decompressor decompressor;

        Singelton() {
            decompressor = new Decompressor();
        }
    }
}
