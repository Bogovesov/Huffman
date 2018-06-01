package com.company;

import java.io.IOException;

public class Application {

    public static final String COMPRESS = "--compress";
    public static final String DECOMPRESS = "--decompress";

    private Application() {

    }

    public static Application instance() {
        return Singelton.INSTANCE.application;
    }

    public void run(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Wrong parameters");
        }
        final String fileName = args[0];
        final String mode = args[1];
        if (mode.equals(COMPRESS)) {
            Compressor.instance().compress(fileName);
        } else if (mode.equals(DECOMPRESS)) {
            try {
                Decompressor.instamce().decompress(fileName);
            } catch (UnexpectedFileFormat e) {
                e.printStackTrace();
            }
        }
    }

    private enum Singelton {
        INSTANCE;
        private Application application;

        Singelton() {
            application = new Application();
        }
    }
}
