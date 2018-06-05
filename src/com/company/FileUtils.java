package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static final String EXT_COMPRESSED = ".compressed";
    public static final String EXT_DECOMPRESSED = ".decompressed";
    public static final String EXT_META = ".meta";

    public static byte[] readBytes(String fileName) {
        File file = new File(fileName);
        byte[] content = new byte[0];
        try (FileInputStream fis = new FileInputStream(file)) {
            content = new byte[fis.available()];
            fis.read(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static void writeBytes(String fileName, byte[] content) {
        try (FileOutputStream fileOuputStream = new FileOutputStream(fileName)) {
            fileOuputStream.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: реализовать проверку на валидность
    public static boolean isValidFormat(byte[] content) {
        return true;
    }
}
