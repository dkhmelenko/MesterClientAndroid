package com.khmelenko.lab.mester.network.request;

import com.google.gson.annotations.SerializedName;

/**
 * Defines the structure for the request TestCase
 *
 * @author Dmytro Khmelenko
 */
public class TestCaseRequest {

    @SerializedName("projectId")
    private String projectId;

    @SerializedName("title")
    private String title;

    public String getProjectId() {
        return projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
