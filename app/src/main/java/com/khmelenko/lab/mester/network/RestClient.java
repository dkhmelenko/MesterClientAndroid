package com.khmelenko.lab.mester.network;

import com.khmelenko.lab.mester.network.response.ProjectResponse;

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
}
