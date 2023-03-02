
package com.shaktipumps.shakti_rms.bean.Uspc;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@SuppressWarnings("unused")
public class UspcModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private List<UspcModelResponse> mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<UspcModelResponse> getResponse() {
        return mResponse;
    }

    public void setResponse(List<UspcModelResponse> response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
