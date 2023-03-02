
package com.shaktipumps.shakti_rms.bean.GetPlant;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@SuppressWarnings("unused")
public class GetPlantModelView {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private List<GetPlantResponse> mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMassage() {
        return mMessage;
    }

    public void setMassage(String massage) {
        mMessage = massage;
    }

    public List<GetPlantResponse> getResponse() {
        return mResponse;
    }

    public void setResponse(List<GetPlantResponse> response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
