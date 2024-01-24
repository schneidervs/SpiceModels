package com.example.modelextractor.Temp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ConsoleExtractor {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File("input.txt"));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith(".model")) {
                String modelName = line.substring(6).trim();
                FileWriter writer = getFileWriter(modelName, line);
                writer.close();
            }
        }
        scanner.close();
    }

    private static FileWriter getFileWriter(String modelName, String line) throws IOException {
        String[] words = modelName.split(" ");
        modelName = words[0];
        File outputFile = new File(modelName + ".txt");
        if (outputFile.exists()) {
            outputFile = new File("_1" + modelName + ".txt");
        }
        FileWriter writer;
        try {
            writer = new FileWriter(outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writer.write(line);
        return writer;
    }
}
