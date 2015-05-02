package com.khmelenko.lab.mester.network.retrofit;

import com.khmelenko.lab.mester.network.request.AddProjectRequest;
import com.khmelenko.lab.mester.network.request.AddStepRequest;
import com.khmelenko.lab.mester.network.request.AddTestCaseRequest;
import com.khmelenko.lab.mester.network.request.PostTestingRequest;
import com.khmelenko.lab.mester.network.response.ProjectResponse;
import com.khmelenko.lab.mester.network.response.StepResponse;
import com.khmelenko.lab.mester.network.response.TestCaseResponse;
import com.khmelenko.lab.mester.network.response.TestingResponse;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
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
    public void postProject(@Body AddProjectRequest request, Callback<Object> callback);

    @GET("/project/{id}/testcases")
    public void getProjectTestcases(@Path("id") String projectId, Callback<List<TestCaseResponse>> callback);

    @GET("/testcase/{id}")
    public void getTestcaseById(@Path("id") String testcaseId, Callback<TestCaseResponse> callback);

    @DELETE("/testcase/{id}")
    public void deleteTestcaseById(@Path("id") String testcaseId, Callback<Object> callback);

    @POST("/testcase")
    public void postTestcase(@Body AddTestCaseRequest request, Callback<Object> callback);

    @GET("/testcase/{id}/steps")
    public void getTestcaseSteps(@Path("id") String testcaseId, Callback<List<StepResponse>> callback);

    @DELETE("/step/{id}")
    public void deleteStepById(@Path("id") String stepId, Callback<Object> callback);

    @POST("/step")
    public void postStep(@Body AddStepRequest request, Callback<Object> callback);

    @GET("/project/{id}/tests")
    public void getTestingResults(@Path("id") String projectId, Callback<List<TestingResponse>> callback);

    @PUT("/test/{id}/submit")
    public void postTestingResults(@Path("id") String testId, @Body PostTestingRequest request,
                                   Callback<TestingResponse> callback);

    @POST("/project/{id}/test")
    public void generateTests(@Path("id") String projectId, Callback<TestingResponse> callback);
}
