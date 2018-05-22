package com.spnthreatviz;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Date;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
     * Send a test message
     * @param message the message to send
     * @return true iff success.
     * @exception STVException.STVServiceException if generic error.
     */
    public Boolean getTestMessage(String message) throws STVException.STVServiceException {
        System.out.println("logoutUser: Running");

        return true;
    }

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
