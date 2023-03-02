
package com.shaktipumps.shakti_rms.bean.HomeDeviceListBean;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class HomeDeviceListModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private List<HomeDeviceListResponse> mResponse;
    @SerializedName("status")
    private boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<HomeDeviceListResponse> getResponse() {
        return mResponse;
    }

    public void setResponse(List<HomeDeviceListResponse> response) {
        mResponse = response;
    }

    public boolean getStatus() {
        return mStatus;
    }

    public void setStatus(boolean status) {
        mStatus = status;
    }

}
