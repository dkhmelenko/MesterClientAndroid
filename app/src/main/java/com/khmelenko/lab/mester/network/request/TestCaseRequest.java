package com.khmelenko.lab.mester.network.request;

import com.google.gson.annotations.SerializedName;

/**
 * Defines the structure for the request TestCase
 *
 * @author Dmytro Khmelenko
 */
public class TestCaseRequest {

    @SerializedName("projectId")
    private String mProjectId;

    @SerializedName("title")
    private String mTitle;

    public String getProjectId() {
        return mProjectId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setProjectId(String projectId) {
        mProjectId = projectId;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
