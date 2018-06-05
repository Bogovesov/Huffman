package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.company.FileUtils.*;

public class Meta {

    public static void write(String fileName, int[] frequecny) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < frequecny.length; i++) {
                if (frequecny[i] != 0) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(i).append(":").append(frequecny[i]).append("\n");
                    writer.write(builder.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static int[] read(String fileName) {
        final String fileNameMeta = fileName.replaceAll(EXT_COMPRESSED, EXT_META);
        int[] frequecny = new int[255];
        try {
            final List<String> strings = Files.readAllLines(Paths.get(fileNameMeta));
            Map<Integer, String> codeTable = new HashMap<>();
            for (String str : strings) {
                String[] pivot = str.split(":");
                codeTable.put(Integer.valueOf(pivot[0]), pivot[1]);
            }
            for (int i = 0; i < frequecny.length; i++) {
                if (codeTable.containsKey(i)) {
                    frequecny[i] = Integer.parseInt(codeTable.get(i));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return frequecny;
    }
}
