package com.khmelenko.lab.mester.network.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.khmelenko.lab.mester.common.Constants;
import com.khmelenko.lab.mester.network.OnRestCallComplete;
import com.khmelenko.lab.mester.network.RestClient;
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
    public RestApiService getmRestApiService() {
        return mRestApiService;
    }

    @Override
    public void getProjects(final OnRestCallComplete<List<ProjectResponse>> callback) {
        mRestApiService.getProjects(new Callback<List<ProjectResponse>>() {
            @Override
            public void success(List<ProjectResponse> projectResponses, Response response) {
                if (callback != null) {
                    callback.onSuccess(projectResponses);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null && error != null) {
                    callback.onFail(123, error.getMessage());
                }

            }
        });
    }
}
