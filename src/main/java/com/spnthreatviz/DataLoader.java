package com.spnthreatviz;

import java.io.File;
import com.google.gson.JsonArray;

/**
 * Executable that loads the hardcoded NVD CVE file into a String and processes it.
 */
public class DataLoader {

    public static void main(String[] args) throws STVException.CVEParsingException {
        //Initialize Database Manager so that if no db files exist, they are created.
        DatabaseManager.purge();
        DatabaseManager.initialize();
        
        //Debug
        //listAllFiles(new File("."));

        //Specify data files to load here
        //It may be better to have a single file in /resources that contains newline delimited paths to all the files to load
        String filepath = "nvdcve/nvdcve-1.0-recent.json";

        //Load to a String
        String json = (new JsonFileReader()).getFile(filepath);

        //Call CVEParser, which will handle loading to database
        CVEParser cvep = new CVEParser();
        JsonArray cveobjs = cvep.getCVEItemsAsArray(json);
        cvep.loadCVEItemsToObjects(cveobjs);
    }

    /**
     * Prints contents of current directory. Used for debug. Copied from https://stackoverflow.com/questions/15482423/.
     * @param curDir
     */
    private static void listAllFiles(File curDir) {
        File[] filesList = curDir.listFiles();
        for(File f : filesList){
            if(f.isDirectory())
                System.out.println(f.getName());
            if(f.isFile()){
                System.out.println(f.getName());
            }
        }
    }
    
}
