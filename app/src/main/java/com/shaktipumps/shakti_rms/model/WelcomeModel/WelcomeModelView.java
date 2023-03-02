
package com.shaktipumps.shakti_rms.model.WelcomeModel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class WelcomeModelView {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private List<WelcomeModelResponse> mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<WelcomeModelResponse> getResponse() {
        return mResponse;
    }

    public void setResponse(List<WelcomeModelResponse> response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
