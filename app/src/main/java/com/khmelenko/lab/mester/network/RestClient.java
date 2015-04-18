package com.khmelenko.lab.mester.network;

import com.khmelenko.lab.mester.network.response.ProjectResponse;
import com.khmelenko.lab.mester.network.response.TestCaseResponse;

import java.util.List;

/**
 * Defines an interface for the REST client
 *
 * @author Dmytro Khmelenko
 */
public interface RestClient {

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
}
