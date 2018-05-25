package com.company;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static final String FILE_NAME = "d:" + File.separator + "text.txt";

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input: \n1 - for compress:");
            String input = scanner.nextLine();
            if (input.equals("1")) {
                doCompressDecompress(FILE_NAME, true);
            } else {
                doCompressDecompress(FILE_NAME + ".compressed", false);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void doCompressDecompress(String fileName, boolean compression) throws IOException, ClassNotFoundException {
        if (compression) {
            Compressor compressor = new Compressor();
            compressor.compress(fileName);
        } else {
            Decompressor decompressor = new Decompressor();
            decompressor.decompress(fileName);
        }
    }
}
