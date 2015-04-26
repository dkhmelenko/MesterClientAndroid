package com.khmelenko.lab.mester.network.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Posts testing test case request
 *
 * @author Dmytro Khmelenko
 */
public class PostTestingTestCaseRequest {

    @SerializedName("id")
    private String mId;

    @SerializedName("stepTests")
    private List<PostTestingStepRequest> mSteps;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public List<PostTestingStepRequest> getSteps() {
        return mSteps;
    }

    public void setSteps(List<PostTestingStepRequest> steps) {
        mSteps = steps;
    }
}
