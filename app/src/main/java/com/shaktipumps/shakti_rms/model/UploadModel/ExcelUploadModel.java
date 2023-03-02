
package com.shaktipumps.shakti_rms.model.UploadModel;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ExcelUploadModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private Response mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Response getResponse() {
        return mResponse;
    }

    public void setResponse(Response response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
