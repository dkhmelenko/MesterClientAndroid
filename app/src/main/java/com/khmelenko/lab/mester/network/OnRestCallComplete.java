package com.khmelenko.lab.mester.network;

/**
 * Defines an interface for the REST call completion
 *
 * @author Dmytro Khmelenko
 */
public interface OnRestCallComplete<T> {

    /**
     * Called on successful call completion
     *
     * @param responseBody Response body
     */
    void onSuccess(T responseBody);

    /**
     * Called when the call failed
     *
     * @param errorCode Error code
     * @param message   Error message
     */
    void onFail(int errorCode, String message);
}
