package com.spnthreatviz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

import static spark.Spark.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/** 
 * The main executable class. Run this to set up the application.
 */
public class Bootstrap {
    /**
     * IP Address of the server.
     */
    private static final String IP_ADDRESS = "localhost";

    /**
     * Port to serve the website on.
     */
    private static final int PORT = 8080;

    /**
     * Logger used for catching errors caused by failed startup.
     */
    private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    /**
     * Main method of Bootstrap, which sets up the server and serves the website.
     * @param args command line arguments to the main method.
     * @throws Exception if there is an issue setting up the server or website.
     */
    public static void main(String[] args) throws Exception {
        //Specify the IP address and Port at which the server should be run
        ipAddress(IP_ADDRESS);
        port(PORT);

        //Specify the sub-directory from which to serve static resources (like html and css)
        staticFileLocation("/public");

        //Create the model instance and then configure and start the web service
        try {
            STVService model = new STVService();
            new STVController(model);
        } catch (Exception ex) {
            logger.error("Failed to create a Spn-ThreatViz instance. Aborting");
        }
    }
}
