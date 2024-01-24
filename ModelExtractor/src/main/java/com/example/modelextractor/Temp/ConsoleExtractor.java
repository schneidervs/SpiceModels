package com.example.modelextractor.Temp;

import java.io.*;
import java.util.Scanner;

public class ConsoleExtractor {
    public static void main(String[] args) throws IOException {
        String inputFile = "input.txt";
        String outputFile = "output.txt";

        try {
            replaceSubstring(inputFile, outputFile);
            System.out.println("Программа успешно завершена.");
        } catch (IOException e) {
            System.err.println("Ошибка при выполнении программы: " + e.getMessage());
        }
        modelExtract();
    }

    private static void replaceSubstring(String inputFile, String outputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            StringBuilder modelLine = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("*")) continue;
                line = line.trim().startsWith("+") ? line.substring(1).trim() : line.trim();
                line = !line.contains(";") ? line : line.substring(0, line.indexOf(";")).trim();
                modelLine.append(line);

                if (line.endsWith(")")) {
                    writer.write(modelLine.toString());
                    writer.newLine();
                    modelLine.setLength(0);
                }
            }
        }
    }

    private static void modelExtract() throws IOException {
        Scanner scanner = new Scanner(new File("output.txt"));
        while (scanner.hasNextLine()) {
            String lineFirst = scanner.nextLine().trim();
            if (lineFirst.startsWith(".model") || lineFirst.startsWith(".MODEL")) {
                String modelName = lineFirst.substring(6).trim();
                FileWriter writer = getFileWriter(modelName, lineFirst);
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
            outputFile = new File("_copy_" + modelName + ".txt");
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
