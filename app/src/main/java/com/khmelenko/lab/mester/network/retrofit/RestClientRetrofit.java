package com.khmelenko.lab.mester.network.retrofit;

import com.khmelenko.lab.mester.common.Constants;
import com.khmelenko.lab.mester.network.OnRestCallComplete;
import com.khmelenko.lab.mester.network.RestClient;
import com.khmelenko.lab.mester.network.response.ProjectResponse;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
        RestAdapter restAdapter = new RestAdapter.Builder()
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
    public RestApiService getmRestApiService() {
        return mRestApiService;
    }

    @Override
    public void getProjects(String projectId, final OnRestCallComplete<List<ProjectResponse>> callback) {
        mRestApiService.getProjects(new Callback<List<ProjectResponse>>() {
            @Override
            public void success(List<ProjectResponse> projectResponses, Response response) {
                if (callback != null) {
                    callback.onSuccess(projectResponses);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.onFail(error.getResponse().getStatus(), error.getMessage());
                }

            }
        });
    }
}
