
package com.shaktipumps.shakti_rms.bean;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class AddPlantModelView {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private AddPlantResponse mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public AddPlantResponse getResponse() {
        return mResponse;
    }

    public void setResponse(AddPlantResponse response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
