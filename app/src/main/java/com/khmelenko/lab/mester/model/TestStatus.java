package com.khmelenko.lab.mester.model;

/**
 * Defines test status
 *
 * @author Dmytro Khmelenko
 */
public enum TestStatus {

    DEFAULT("default"),
    PASSED("passed"),
    FAILED("failed");

    // status name
    private String mName;

    private TestStatus(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }
}
