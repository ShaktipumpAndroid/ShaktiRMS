
package com.shaktipumps.shakti_rms.bean.Simha_graph;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class SimhaGraphModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private List<SimhaGraphViewResponse> mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<SimhaGraphViewResponse> getResponse() {
        return mResponse;
    }

    public void setResponse(List<SimhaGraphViewResponse> response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
