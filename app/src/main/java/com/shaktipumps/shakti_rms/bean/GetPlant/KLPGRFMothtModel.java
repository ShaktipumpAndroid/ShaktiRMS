
package com.shaktipumps.shakti_rms.bean.GetPlant;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class KLPGRFMothtModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private List<KLPGRFMothtResponse> mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<KLPGRFMothtResponse> getResponse() {
        return mResponse;
    }

    public void setResponse(List<KLPGRFMothtResponse> response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
