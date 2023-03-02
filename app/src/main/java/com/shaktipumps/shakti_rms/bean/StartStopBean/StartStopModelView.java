
package com.shaktipumps.shakti_rms.bean.StartStopBean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class StartStopModelView {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private List<StartStopVkResponse> mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<StartStopVkResponse> getResponse() {
        return mResponse;
    }

    public void setResponse(List<StartStopVkResponse> response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
