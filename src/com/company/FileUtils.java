package com.company;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static final String EXT_COMPRESSED = ".compressed";
    public static final String EXT_DECOMPRESSED = ".decompressed";
    public static final String EXT_META = ".meta";

    public static List<String> read(String fileName) {
        List<String> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String text;
            while ((text = reader.readLine()) != null) {
                result.add(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void save(String fileName, String text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String convertListToString(List<String> strings) {
        String resultStr = "";
        for (String string : strings) {
            resultStr += string;
        }
        return resultStr;
    }

    public static void saveMetaData(String fileName, HuffmanTree huffmanTree) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName + EXT_META);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(huffmanTree.getRoot());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: реализовать проверку на валидность
    public static boolean isValidFormat(String decodeString) {
        return true;
    }
}
