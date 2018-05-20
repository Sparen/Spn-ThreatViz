package com.spnthreatviz;

/**
 * Container class for STV Exceptions.
 */
public class STVException {

    /**
     * Construct a STVException instance.
     */
    public STVException() {
    }

    /**
     * General purpose exception. Linked with 500.
     */
    public static class STVServiceException extends Exception {
        public STVServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Exception thrown when user input is incomplete. (ex: missing fields). Linked with 400.
     */
    public static class MissingParametersException extends Exception {
        public MissingParametersException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Exception thrown when user input is invalid. Linked with 400.
     */
    public static class InvalidInputException extends Exception {
        public InvalidInputException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
