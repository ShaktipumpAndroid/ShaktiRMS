
package com.shaktipumps.shakti_rms.model.UploadModel;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ProfileUpdateModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private Long mResponse;
    @SerializedName("status")
    private String mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Long getResponse() {
        return mResponse;
    }

    public void setResponse(Long response) {
        mResponse = response;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
