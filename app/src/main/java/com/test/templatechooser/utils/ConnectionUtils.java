package com.test.templatechooser.utils;

import java.net.UnknownHostException;

/**
 * The util class is used to help with connection checks.
 * */
public class ConnectionUtils {

    /**
     * Checks if the throwable is an instance of {@link UnknownHostException}, which means
     * the device has no connection.
     *
     * @param throwable The throwable error.
     * @return True if it has no connection available, false otherwise.
     * */
    public static boolean noInternetAvailable(Throwable throwable) {
        return throwable instanceof UnknownHostException;
    }

}
