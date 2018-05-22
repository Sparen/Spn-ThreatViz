package com.spnthreatviz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

import static spark.Spark.*;

/**
 * Controller class used for API calls. 
 */
public class STVController {

    /**
     * String containing the common part of all API calls.
     */
    private static final String API_CONTEXT = "api/stv";

    /**
     * STVService instance linked to the controller.
     */
    private final STVService stvService;

    /**
     * Logger used for responses and error codes.
     */
    private final Logger logger = LoggerFactory.getLogger(STVController.class);
    
    /**
     * Create a STVController instance.
     * @param stvService STVService instance linked to the controller
     */
    public STVController(STVService stvService) {
        this.stvService = stvService;
        setupEndpoints();
    }

    //EXCEPTIONS: 500 error code is used EXCLUSIVELY for the generic STVServiceException, 
    //which should only be used when the error is too generic and does not fit into a more specific category.

    /**
     * State and handle the processing of all API Calls.
     */
    private void setupEndpoints() {
        get(API_CONTEXT + "/test", "application/json", (request, response)-> {
            try {
                response.status(200);
                return stvService.getTestMessage("This is a Testing Message.");
            } catch (STVException.STVServiceException ex) {
                logger.error("STVServiceException. Failed to get test message. " + ex.getMessage());
                response.status(500);
                return Collections.EMPTY_MAP;
            }
        }, new JsonTransformer());
    }
}
