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
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("creationDate")
    private String creationDate;

    @SerializedName("projectId")
    private String projectId;

    @SerializedName("steps")
    private List<StepResponse> steps;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getProjectId() {
        return projectId;
    }

    public List<StepResponse> getSteps() {
        return steps;
    }
}
