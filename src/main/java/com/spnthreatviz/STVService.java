package com.spnthreatviz;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;

import com.google.gson.Gson;

/**
 * Service class used by controller for basic functions of the application.
 */
public class STVService {

    /**
     * Constructs the STVService object for processing of basic application functions
     */
    public STVService() {
        DatabaseManager.initialize(); //Initialize Database Manager so that it creates a database if none exists
    }

     /**
     * Send a test message.
     * @param message the message to send.
     * @return message passed as input.
     * @exception STVException.STVServiceException if generic error.
     */
    public String getTestMessage(String message) throws STVException.STVServiceException {
        System.out.println("getTestMessage: Running");

        return message;
    }

    /**
     * Run a query on the database with the provided search terms and return the results.
     * Note that if there are multiple entries with the same vendor, productName, and description, they may be treated as duplicates and not included.
     * @param searchTerms the string delimited search terms to utilize.
     * @return results of search query as an ArrayList.
     * @exception STVException.STVServiceException if generic error.
     */
    public ArrayList<CVEObject> getSearchResult(String searchTerms) throws STVException.STVServiceException {
        System.out.println("getSearchResult: Running");

        //First, force lowercase to comply with CVE format and enable more consistent results
        String lcSearchTerms = searchTerms.toLowerCase();

        //Delimit search terms by spaces
        String[] terms = lcSearchTerms.split("\\s+"); //delimit by spaces

        ArrayList<CVEObject> outputs = new ArrayList<>(); //will hold all queries to return

        //For every search term, run the search.
        for (int i = 0; i < terms.length; i += 1) {
            ArrayList<CVEObject> ret = DatabaseManager.getCVESearch(terms[i]);
            //For all of those that do not exist in outputs, add to outputs. Note: utilizes CVEObject equals method
            //Note that this utilizes OR on the terms rather than AND
            for (CVEObject cveo : ret) {
                if (!outputs.contains(cveo)) {
                    outputs.add(cveo);
                }
            }
        }

        return outputs;
    }

    //TODO: Method and API call for inserting new data - Low Priority due to preloaded nature of database

    /* **************** HELPER FUNCTIONS **************** */

    /** 
     * Check if input String is valid.
     * @param input the string to test.
     * @return true if valid, false otherwise.
     */
    public boolean isValidInput(String input) {
        return input != null && !input.equals("");
    }
}
