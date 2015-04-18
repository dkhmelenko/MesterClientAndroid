package com.khmelenko.lab.mester.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Defines the response with test case structure
 *
 * @author Dmytro Khmelenko
 */
public class TestCaseResponse {

    @SerializedName("id")
    private String mId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("creationDate")
    private String mCreationDate;

    @SerializedName("projectId")
    private String mProjectId;

    @SerializedName("steps")
    private List<StepResponse> mSteps;

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getCreationDate() {
        return mCreationDate;
    }

    public String getProjectId() {
        return mProjectId;
    }

    public List<StepResponse> getSteps() {
        return mSteps;
    }
}
