package com.khmelenko.lab.mester.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Defines the structure for the project response
 *
 * @author Dmytro Khmelenko
 */
public class ProjectResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("creationDate")
    private String creationDate;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCreationDate() {
        return creationDate;
    }
}
