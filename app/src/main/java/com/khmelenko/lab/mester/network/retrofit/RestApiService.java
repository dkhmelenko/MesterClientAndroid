package com.khmelenko.lab.mester.network.retrofit;

import com.khmelenko.lab.mester.network.request.ProjectRequest;
import com.khmelenko.lab.mester.network.request.TestCaseRequest;
import com.khmelenko.lab.mester.network.response.ProjectResponse;
import com.khmelenko.lab.mester.network.response.TestCaseResponse;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Defines REST API interface
 *
 * @author Dmytro Khmelenko
 */
interface RestApiService {

    @GET("/projects")
    public void getProjects(Callback<List<ProjectResponse>> callback);

    @GET("/project/{id}")
    public void getProjectById(@Path("id") String projectId, Callback<ProjectResponse> callback);

    @DELETE("/project/{id}")
    public void deleteProjectById(@Path("id") String projectId, Callback<Object> callback);

    @POST("/project")
    public void postProject(@Body ProjectRequest request, Callback<Object> callback);

    @GET("/project/{id}/testcases")
    public void getProjectTestcases(@Path("id") String projectId, Callback<List<TestCaseResponse>> callback);

    @GET("/testcase/{id}")
    public void getTestcaseById(@Path("id") String testcaseId, Callback<TestCaseResponse> callback);

    @DELETE("/testcase/{id}")
    public void deleteTestcaseById(@Path("id") String testcaseId, Callback callback);

    @POST("/testcase")
    public void postTestcase(@Body TestCaseRequest request, Callback callback);
}
