
package com.shaktipumps.shakti_rms.bean.GetPlant;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class GetPlantResponse {

    @SerializedName("isActive")
    private Boolean mIsActive;
    @SerializedName("mUserId")
    private String mMUserId;
    @SerializedName("pid")
    private String mPid;
    @SerializedName("plantAddress")
    private String mPlantAddress;
    @SerializedName("plantName")
    private String mPlantName;
    @SerializedName("capacity")
    private String mCapacity;



    public Boolean getIsActive() {
        return mIsActive;
    }

    public void setIsActive(Boolean isActive) {
        mIsActive = isActive;
    }

    public String getMUserId() {
        return mMUserId;
    }

    public void setMUserId(String mUserId) {
        mMUserId = mUserId;
    }

    public String getPid() {
        return mPid;
    }

    public void setPid(String pid) {
        mPid = pid;
    }

    public String getPlantAddress() {
        return mPlantAddress;
    }

    public void setPlantAddress(String plantAddress) {
        mPlantAddress = plantAddress;
    }

    public String getPlantName() {
        return mPlantName;
    }

    public void setPlantName(String plantName) {
        mPlantName = plantName;
    }

    public String getCapacity() {
        return mCapacity;
    }

    public void setCapacity(String capacity) {
        mCapacity = capacity;
    }

}
