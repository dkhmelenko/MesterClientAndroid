package com.khmelenko.lab.mester.network.response;

import com.google.gson.annotations.SerializedName;

/**
 * Defines the structure of the steps inside testing test case
 *
 * @author Dmytro Khmelenko
 */
public class TestingStepResponse {

    @SerializedName("id")
    private String mId;

    @SerializedName("status")
    private String mStatus;

    @SerializedName("creationDate")
    private String mCreationDate;

    @SerializedName("testStepId")
    private String mTestStepId;

    @SerializedName("caseTestId")
    private String mCaseTestId;

    private String mDescription;

    public String getId() {
        return mId;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getCreationDate() {
        return mCreationDate;
    }

    public String getTestStepId() {
        return mTestStepId;
    }

    public String getCaseTestId() {
        return mCaseTestId;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
