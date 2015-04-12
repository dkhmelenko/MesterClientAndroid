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
     * @param projectId Project ID
     */
    void getProjects(String projectId, OnRestCallComplete<List<ProjectResponse>> callback);

}
