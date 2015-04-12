package com.khmelenko.lab.mester.network.request;

import com.google.gson.annotations.SerializedName;

/**
 * Defines the request for adding new project
 *
 * @author Dmytro Khmelenko
 */
public class ProjectRequest {

    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }
}
