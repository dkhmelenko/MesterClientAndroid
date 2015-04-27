package com.khmelenko.lab.mester.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Defines the response with tests
 *
 * @author Dmytro Khmelenko
 */
public class TestingResponse {

    @SerializedName("id")
    private String mId;

    @SerializedName("status")
    private String mStatus;

    @SerializedName("creationDate")
    private String mCreationDate;

    @SerializedName("startDate")
    private String mStartDate;

    @SerializedName("endDate")
    private String mEndDate;

    @SerializedName("projectId")
    private String mProjectId;

    @SerializedName("caseTests")
    private List<TestingTestCaseResponse> mTestCases;

    public String getId() {
        return mId;
    }

    public String getStatus() {
        return mStatus;
    }

    public String getCreationDate() {
        return mCreationDate;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public String getEndDate() {
        return mEndDate;
    }

    public String getProjectId() {
        return mProjectId;
    }

    public List<TestingTestCaseResponse> getTestCases() {
        return mTestCases;
    }
}
