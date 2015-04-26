package com.khmelenko.lab.mester.network.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Defines the request for posting testing results
 *
 * @author Dmytro Khmelenko
 */
public class PostTestingRequest {

    @SerializedName("caseTests")
    private List<PostTestingTestCaseRequest> mTestCases;

    public List<PostTestingTestCaseRequest> getTestCases() {
        return mTestCases;
    }

    public void setTestCases(List<PostTestingTestCaseRequest> testCases) {
        mTestCases = testCases;
    }
}
