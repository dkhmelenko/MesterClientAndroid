package com.khmelenko.lab.mester.network.request;

import com.google.gson.annotations.SerializedName;

/**
 * Posts testing step request
 *
 * @author Dmytro Khmelenko
 */
public class PostTestingStepRequest {

    @SerializedName("id")
    private String mId;

    @SerializedName("status")
    private String mStatus;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }
}
