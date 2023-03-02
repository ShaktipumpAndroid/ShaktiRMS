
package com.shaktipumps.shakti_rms.bean.ProductStausBean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ProductStatusResponse {

    @SerializedName("DeviceType")
    private String mDeviceType;
    @SerializedName("SColor")
    private String mSColor;
    @SerializedName("SDescription")
    private String mSDescription;
    @SerializedName("SMode")
    private String mSMode;
    @SerializedName("SName")
    private String mSName;
    @SerializedName("Sid")
    private String mSid;
    @SerializedName("Status")
    private Boolean mStatus;

    public String getDeviceType() {
        return mDeviceType;
    }

    public void setDeviceType(String deviceType) {
        mDeviceType = deviceType;
    }

    public String getSColor() {
        return mSColor;
    }

    public void setSColor(String sColor) {
        mSColor = sColor;
    }

    public String getSDescription() {
        return mSDescription;
    }

    public void setSDescription(String sDescription) {
        mSDescription = sDescription;
    }

    public String getSMode() {
        return mSMode;
    }

    public void setSMode(String sMode) {
        mSMode = sMode;
    }

    public String getSName() {
        return mSName;
    }

    public void setSName(String sName) {
        mSName = sName;
    }

    public String getSid() {
        return mSid;
    }

    public void setSid(String sid) {
        mSid = sid;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
