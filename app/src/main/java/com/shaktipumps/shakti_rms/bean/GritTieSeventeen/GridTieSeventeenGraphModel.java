
package com.shaktipumps.shakti_rms.bean.GritTieSeventeen;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class GridTieSeventeenGraphModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private List<GridTieSeventeenGraphResponse> mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<GridTieSeventeenGraphResponse> getResponse() {
        return mResponse;
    }

    public void setResponse(List<GridTieSeventeenGraphResponse> response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
