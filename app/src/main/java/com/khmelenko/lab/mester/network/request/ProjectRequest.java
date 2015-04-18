package com.khmelenko.lab.mester.network.request;

import com.google.gson.annotations.SerializedName;

/**
 * Defines the request for adding new project
 *
 * @author Dmytro Khmelenko
 */
public class ProjectRequest {

    @SerializedName("name")
    private String mName;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
