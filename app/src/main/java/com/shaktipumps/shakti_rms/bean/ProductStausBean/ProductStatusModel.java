
package com.shaktipumps.shakti_rms.bean.ProductStausBean;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ProductStatusModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private List<ProductStatusResponse> mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<ProductStatusResponse> getResponse() {
        return mResponse;
    }

    public void setResponse(List<ProductStatusResponse> response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
