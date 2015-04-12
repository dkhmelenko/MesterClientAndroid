package com.khmelenko.lab.mester.network.response;

import com.google.gson.annotations.SerializedName;

/**
 * Defines the structure for the Step response
 *
 * @author Dmytro Khmelenko
 */
public class StepResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("number")
    private int number;

    @SerializedName("text")
    private String text;

    @SerializedName("creationDate")
    private String creationDate;

    @SerializedName("testCaseId")
    private String testCaseId;

    public String getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public String getText() {
        return text;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getTestCaseId() {
        return testCaseId;
    }
}
