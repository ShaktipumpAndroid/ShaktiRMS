
package com.shaktipumps.shakti_rms.bean.MapBean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class MapModelView {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private List<MapDeatialsResponse> mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<MapDeatialsResponse> getResponse() {
        return mResponse;
    }

    public void setResponse(List<MapDeatialsResponse> response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
