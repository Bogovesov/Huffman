package com.company;

public class Application {

    private static final String COMPRESS = "--compress";
    private static final String DECOMPRESS = "--decompress";

    private Application() {

    }

    public static Application instance() {
        return Singleton.INSTANCE.application;
    }

    public void run(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Wrong parameters");
        }
        final String fileName = args[0];
        final String mode = args[1];
        if (COMPRESS.equals(mode)) {
            Compressor.instance().compress(fileName);
        } else if (DECOMPRESS.equals(mode)) {
            try {
                Decompressor.instance().decompress(fileName);
            } catch (UnexpectedFileFormat e) {
                e.printStackTrace();
            }
        }
    }

    private enum Singleton {
        INSTANCE;
        private final Application application;

        Singleton() {
            application = new Application();
        }
    }
}
