package com.khmelenko.lab.mester.network.request;

import com.google.gson.annotations.SerializedName;

/**
 * Defines a request for adding new step
 *
 * @author Dmytro Khmelenko
 */
public class AddStepRequest {

    @SerializedName("number")
    private int mNumber;

    @SerializedName("testCaseId")
    private String mTestcaseId;

    @SerializedName("text")
    private String mText;

    public int getNumber() {
        return mNumber;
    }

    public void setNumber(int number) {
        mNumber = number;
    }

    public String getTestcaseId() {
        return mTestcaseId;
    }

    public void setTestcaseId(String testcaseId) {
        mTestcaseId = testcaseId;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }
}
