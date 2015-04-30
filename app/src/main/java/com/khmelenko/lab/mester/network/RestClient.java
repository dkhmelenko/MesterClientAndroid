package com.khmelenko.lab.mester.network;

import com.khmelenko.lab.mester.network.request.PostTestingRequest;
import com.khmelenko.lab.mester.network.response.ProjectResponse;
import com.khmelenko.lab.mester.network.response.StepResponse;
import com.khmelenko.lab.mester.network.response.TestCaseResponse;
import com.khmelenko.lab.mester.network.response.TestingResponse;

import java.util.List;

/**
 * Defines an interface for the REST client
 *
 * @author Dmytro Khmelenko
 */
public interface RestClient {

    /**
     * Sets endpoint for the REST client
     *
     * @param endpoint Endpoint
     */
    void setEndpoint(String endpoint);

    /**
     * Gets the list of available projects
     *
     * @param callback Callback with the response
     */
    void getProjects(OnRestCallComplete<List<ProjectResponse>> callback);

    /**
     * Adds new project
     *
     * @param projectName Name of the new project
     * @param callback    Callback with the response
     */
    void addProject(String projectName, OnRestCallComplete callback);

    /**
     * Deletes project by ID
     *
     * @param projectId Project ID
     * @param callback  Callback
     */
    void deleteProject(String projectId, OnRestCallComplete callback);

    /**
     * Gets the list of the project testcases
     *
     * @param projectId Project ID
     * @param callback  Callback with the response
     */
    void getTestcases(String projectId, OnRestCallComplete<List<TestCaseResponse>> callback);

    /**
     * Adds new testcase
     *
     * @param projectId     Project ID
     * @param testcaseTitle Title of the new testcase
     * @param callback      Callback with the response
     */
    void addTestcase(String projectId, String testcaseTitle, OnRestCallComplete callback);

    /**
     * Deletes testcase by ID
     *
     * @param testcaseId Testcase ID
     * @param callback   Callback
     */
    void deleteTestcase(String testcaseId, OnRestCallComplete callback);

    /**
     * Gets the list of the testcase steps
     *
     * @param testcaseId Testcase ID
     * @param callback   Callback with the response
     */
    void getTestcaseSteps(String testcaseId, OnRestCallComplete<List<StepResponse>> callback);

    /**
     * Adds new step to the testcase
     *
     * @param testcaseId Testcase ID
     * @param stepNumber Number of the step
     * @param stepText   Text description of the step
     * @param callback   Callback with the response
     */
    void addStep(String testcaseId, int stepNumber, String stepText, OnRestCallComplete callback);

    /**
     * Deletes testcase step by ID
     *
     * @param stepId   Step ID
     * @param callback Callback
     */
    void deleteStep(String stepId, OnRestCallComplete callback);

    /**
     * Gets testing results for specific project
     *
     * @param projectId Project ID
     * @param callback  Callback
     */
    void getTestingResults(String projectId, OnRestCallComplete<List<TestingResponse>> callback);

    /**
     * Posts testing testcase results
     *
     * @param testId   Test ID
     * @param testcase Testcase results
     * @param callback Callback
     */
    void postTestingResults(String testId, PostTestingRequest request,
                            OnRestCallComplete<List<TestingResponse>> callback);

    /**
     * Generates tests for the project
     *
     * @param projectId Project ID
     * @param callback  Callback
     */
    void generateTests(String projectId, OnRestCallComplete<TestingResponse> callback);
}
