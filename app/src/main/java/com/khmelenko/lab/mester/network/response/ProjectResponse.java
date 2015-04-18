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
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("creationDate")
    private String mCreationDate;

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getCreationDate() {
        return mCreationDate;
    }
}
