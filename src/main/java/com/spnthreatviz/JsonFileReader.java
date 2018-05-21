package com.spnthreatviz;

//Code on this page adapted from https://www.mkyong.com/java/java-read-a-file-from-resources-folder/

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.IOUtils;

/**
 * File Reader Class that provides functionality to load the NVD CVE files (or any files really) into strings.
 */
public class JsonFileReader {

    /**
     * Empty Constructor for a JsonFileReader Object.
     */
    public JsonFileReader() {}


    /**
     * Gets the contents of a file and returns them as a String.
     * @param fileName name of the file to load (e.g. /nvdcve/nvdcve-1.0-recent).
     * @return string containing the contents of fileName.
     */
    public String getFile(String fileName) {
        String result = "";
        
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
            
        return result;
    }
}
