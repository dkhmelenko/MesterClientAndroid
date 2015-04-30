package com.khmelenko.lab.mester.network.retrofit;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.khmelenko.lab.mester.common.Constants;
import com.khmelenko.lab.mester.network.OnRestCallComplete;
import com.khmelenko.lab.mester.network.RestClient;
import com.khmelenko.lab.mester.network.request.AddProjectRequest;
import com.khmelenko.lab.mester.network.request.AddStepRequest;
import com.khmelenko.lab.mester.network.request.AddTestCaseRequest;
import com.khmelenko.lab.mester.network.request.PostTestingRequest;
import com.khmelenko.lab.mester.network.request.PostTestingTestCaseRequest;
import com.khmelenko.lab.mester.network.response.ProjectResponse;
import com.khmelenko.lab.mester.network.response.StepResponse;
import com.khmelenko.lab.mester.network.response.TestCaseResponse;
import com.khmelenko.lab.mester.network.response.TestingResponse;
import com.khmelenko.lab.mester.network.response.TestingTestCaseResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Contains implementation of the REST client using Retrofit library
 *
 * @author Dmytro Khmelenko
 */
public final class RestClientRetrofit implements RestClient {

    private RestApiService mRestApiService;

    /**
     * Constructor
     */
    public RestClientRetrofit() {
        RestAdapter restAdapter = createRestAdapter(Constants.DEFAULT_REST_SERVICE);
        mRestApiService = restAdapter.create(RestApiService.class);
    }

    /**
     * Creates REST adapter instance
     *
     * @param endpoint Endpoint
     */
    private RestAdapter createRestAdapter(String endpoint) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(endpoint)
                .build();

        return restAdapter;
    }

    @Override
    public void setEndpoint(String endpoint) {
        if(endpoint == null || endpoint.length() == 0) {
            throw new IllegalArgumentException("Endpoint cannot be null or empty!");
        }

        RestAdapter restAdapter = createRestAdapter(endpoint);
        mRestApiService = restAdapter.create(RestApiService.class);
    }

    @Override
    public void getProjects(final OnRestCallComplete<List<ProjectResponse>> callback) {
        mRestApiService.getProjects(handleResponse(callback));
    }

    @Override
    public void addProject(String projectName, final OnRestCallComplete callback) {
        AddProjectRequest request = new AddProjectRequest();
        request.setName(projectName);
        mRestApiService.postProject(request, handleResponse(callback));
    }

    @Override
    public void deleteProject(String projectId, OnRestCallComplete callback) {
        mRestApiService.deleteProjectById(projectId, handleResponse(callback));
    }

    @Override
    public void getTestcases(String projectId, OnRestCallComplete<List<TestCaseResponse>> callback) {
        mRestApiService.getProjectTestcases(projectId, handleResponse(callback));
    }

    @Override
    public void addTestcase(String projectId, String testcaseTitle, OnRestCallComplete callback) {
        AddTestCaseRequest request = new AddTestCaseRequest();
        request.setProjectId(projectId);
        request.setTitle(testcaseTitle);
        mRestApiService.postTestcase(request, handleResponse(callback));
    }

    @Override
    public void deleteTestcase(String testcaseId, OnRestCallComplete callback) {
        mRestApiService.deleteTestcaseById(testcaseId, handleResponse(callback));
    }

    @Override
    public void getTestcaseSteps(String testcaseId, OnRestCallComplete<List<StepResponse>> callback) {
        mRestApiService.getTestcaseSteps(testcaseId, handleResponse(callback));
    }

    @Override
    public void addStep(String testcaseId, int stepNumber, String stepText, OnRestCallComplete callback) {
        AddStepRequest request = new AddStepRequest();
        request.setTestcaseId(testcaseId);
        request.setNumber(stepNumber);
        request.setText(stepText);
        mRestApiService.postStep(request, handleResponse(callback));
    }

    @Override
    public void deleteStep(String stepId, OnRestCallComplete callback) {
        mRestApiService.deleteStepById(stepId, handleResponse(callback));
    }

    @Override
    public void getTestingResults(String projectId, OnRestCallComplete<List<TestingResponse>> callback) {
        mRestApiService.getTestingResults(projectId, handleResponse(callback));
    }

    @Override
    public void postTestingResults(String testId, PostTestingRequest request, OnRestCallComplete<List<TestingResponse>> callback) {
        mRestApiService.postTestingResults(testId, request, handleResponse(callback));
    }

    @Override
    public void generateTests(String projectId, OnRestCallComplete<TestingResponse> callback) {
        mRestApiService.generateTests(projectId, handleResponse(callback));
    }

    /**
     * Handles response from the Retrofit library
     *
     * @param userCallback User callback for further notification
     * @param <T>
     * @return Retrorit callback
     */
    private <T> Callback<T> handleResponse(final OnRestCallComplete<T> userCallback) {
        Callback<T> callback = new Callback<T>() {
            @Override
            public void success(T t, Response response) {
                if (userCallback != null) {
                    userCallback.onSuccess(t);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("RestClientRetrofit", error.toString());
                if (userCallback != null) {
                    // TODO Fetch error code
                    userCallback.onFail(0, error.getMessage());
                }
            }
        };
        return callback;
    }
}
