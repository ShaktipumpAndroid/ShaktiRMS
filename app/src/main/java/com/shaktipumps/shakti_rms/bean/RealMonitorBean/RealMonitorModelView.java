
package com.shaktipumps.shakti_rms.bean.RealMonitorBean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class RealMonitorModelView {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private List<RealMonitorvkResponse> mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<RealMonitorvkResponse> getResponse() {
        return mResponse;
    }

    public void setResponse(List<RealMonitorvkResponse> response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
