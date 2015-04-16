package com.khmelenko.lab.mester.network.retrofit;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.khmelenko.lab.mester.common.Constants;
import com.khmelenko.lab.mester.network.OnRestCallComplete;
import com.khmelenko.lab.mester.network.RestClient;
import com.khmelenko.lab.mester.network.request.ProjectRequest;
import com.khmelenko.lab.mester.network.response.ProjectResponse;

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
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(Constants.REST_SERVICE)
                .build();

        mRestApiService = restAdapter.create(RestApiService.class);
    }

    /**
     * Gets REST API service
     *
     * @return Rest API service
     */
    public RestApiService getRestApiService() {
        return mRestApiService;
    }

    @Override
    public void getProjects(final OnRestCallComplete<List<ProjectResponse>> callback) {
        mRestApiService.getProjects(handleResponse(callback));
    }

    @Override
    public void addProject(String projectName, final OnRestCallComplete callback) {
        ProjectRequest request = new ProjectRequest();
        request.setName(projectName);
        mRestApiService.postProject(request, handleResponse(callback));
    }

    @Override
    public void deleteProject(String projectId, OnRestCallComplete callback) {
        mRestApiService.deleteProjectById(projectId, handleResponse(callback));
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
