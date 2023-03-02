
package com.shaktipumps.shakti_rms.bean;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class AddPlantResponse {

    @SerializedName("isActive")
    private Boolean mIsActive;
    @SerializedName("latitude")
    private String mLatitude;
    @SerializedName("longitude")
    private String mLongitude;
    @SerializedName("mUserId")
    private String mMUserId;
    @SerializedName("pid")
    private String mPid;
    @SerializedName("plantAddress")
    private String mPlantAddress;
    @SerializedName("plantName")
    private String mPlantName;

    public Boolean getIsActive() {
        return mIsActive;
    }

    public void setIsActive(Boolean isActive) {
        mIsActive = isActive;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String latitude) {
        mLatitude = latitude;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public void setLongitude(String longitude) {
        mLongitude = longitude;
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

}
