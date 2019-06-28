package com.test.templatechooser.exceptions;

/**
 * Exception class based on {@code RuntimeException}.
 * It indicates no internet connection.
 * */
public class NoConnectionException extends RuntimeException {
    public NoConnectionException() {
        super("No internet connection :(");
    }
}
