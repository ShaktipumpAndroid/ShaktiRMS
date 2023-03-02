
package com.shaktipumps.shakti_rms.model.SettingModel;

import java.util.List;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class SettingModelView {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private List<SettingModelResponse> mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<SettingModelResponse> getResponse() {
        return mResponse;
    }

    public void setResponse(List<SettingModelResponse> response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
