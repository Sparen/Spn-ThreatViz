package com.spnthreatviz;

//Code on this page adapted from https://www.mkyong.com/java/java-read-a-file-from-resources-folder/

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * File Reader Class that provides functionality to load the NVD CVE files (or any files really) into strings.
 */
public class JsonFileReader {

    /**
     * Gets the contents of a file and returns them as a String.
     * @param fileName name of the file to load (e.g. /nvdcve/nvdcve-1.0-recent).
     * @return string containing the contents of fileName.
     */
    private String getFile(String fileName) {

        StringBuilder result = new StringBuilder("");

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();

    }

}
