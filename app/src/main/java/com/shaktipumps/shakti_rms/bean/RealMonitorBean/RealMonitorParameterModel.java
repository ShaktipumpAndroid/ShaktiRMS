
package com.shaktipumps.shakti_rms.bean.RealMonitorBean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class RealMonitorParameterModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private List<RealMonitorParameterResponse> mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<RealMonitorParameterResponse> getResponse() {
        return mResponse;
    }

    public void setResponse(List<RealMonitorParameterResponse> response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
