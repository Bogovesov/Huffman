package com.company;

import java.io.IOException;
import java.util.List;

import static com.company.FileUtils.*;

public class Decompressor {

    private Decompressor() {

    }

    public static Decompressor instamce() {
        return Singelton.INSTANCE.decompressor;
    }

    public void decompress(String fileName) throws UnexpectedFileFormat {
        final List<String> inputString = read(fileName);
        if (inputString.size() > 0) {
            final String fileNameSource = fileName.replaceAll(EXT_COMPRESSED, "");
            final String decodeString = HuffmanTree.buildTree(fileNameSource + EXT_META).decode(convertListToString(inputString));

            if (!isValidFormat(decodeString)) {
                throw new UnexpectedFileFormat();
            }
            save(fileName + EXT_DECOMPRESSED, decodeString);
        }
    }

    private enum Singelton {
        INSTANCE;
        private Decompressor decompressor;

        Singelton() {
            decompressor = new Decompressor();
        }
    }
}
