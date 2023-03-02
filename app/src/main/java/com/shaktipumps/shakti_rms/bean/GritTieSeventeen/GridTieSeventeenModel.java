
package com.shaktipumps.shakti_rms.bean.GritTieSeventeen;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class GridTieSeventeenModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private GridTieSeventeenResponse mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public GridTieSeventeenResponse getResponse() {
        return mResponse;
    }

    public void setResponse(GridTieSeventeenResponse response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
