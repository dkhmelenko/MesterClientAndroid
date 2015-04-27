package com.khmelenko.lab.mester.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Defines the structure of the test case in the test
 *
 * @author Dmytro Khmelenko
 */
public class TestingTestCaseResponse {

    @SerializedName("id")
    private String mId;

    @SerializedName("status")
    private String mStatus;

    @SerializedName("creationDate")
    private String mCreationDate;

    @SerializedName("stepTests")
    private List<TestingStepResponse> mSteps;

    @SerializedName("testCaseId")
    private String mTestcaseId;

    @Expose
    private String mName;

    public String getId() {
        return mId;
    }

    public String getStatus() {
        return mStatus;
    }

    public String getCreationDate() {
        return mCreationDate;
    }

    public List<TestingStepResponse> getSteps() {
        return mSteps;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getTestcaseId() {
        return mTestcaseId;
    }
}
