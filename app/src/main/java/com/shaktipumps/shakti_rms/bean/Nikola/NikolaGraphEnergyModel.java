
package com.shaktipumps.shakti_rms.bean.Nikola;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class NikolaGraphEnergyModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private List<NikolaGraphEnergyResponse> mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<NikolaGraphEnergyResponse> getResponse() {
        return mResponse;
    }

    public void setResponse(List<NikolaGraphEnergyResponse> response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
