package com.khmelenko.lab.mester.network.response;

import com.google.gson.annotations.SerializedName;

/**
 * Defines the structure for the Step response
 *
 * @author Dmytro Khmelenko
 */
public class StepResponse {

    @SerializedName("id")
    private String mId;

    @SerializedName("number")
    private int mNumber;

    @SerializedName("text")
    private String mText;

    @SerializedName("creationDate")
    private String mCreationDate;

    @SerializedName("testCaseId")
    private String mTestCaseId;

    public String getId() {
        return mId;
    }

    public int getNumber() {
        return mNumber;
    }

    public String getText() {
        return mText;
    }

    public String getCreationDate() {
        return mCreationDate;
    }

    public String getTestCaseId() {
        return mTestCaseId;
    }
}
