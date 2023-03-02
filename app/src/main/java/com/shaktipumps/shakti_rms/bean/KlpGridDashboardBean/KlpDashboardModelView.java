
package com.shaktipumps.shakti_rms.bean.KlpGridDashboardBean;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class KlpDashboardModelView {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private KlpgridDashboardResponse mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public KlpgridDashboardResponse getResponse() {
        return mResponse;
    }

    public void setResponse(KlpgridDashboardResponse response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
