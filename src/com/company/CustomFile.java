package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public abstract class CustomFile {

    protected List<String> read(String fileName) throws IOException {
        if (!fileName.isEmpty()) {
            File file = new File(fileName);
            if (!file.exists()) {
                throw new FileNotFoundException();
            } else {
                return Files.readAllLines(file.toPath());
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    protected void save(String fileName, List<String> strings) throws IOException {
        if (!fileName.isEmpty()) {
            File file = new File(fileName);
            Files.write(file.toPath(), strings);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
