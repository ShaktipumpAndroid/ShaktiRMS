
package com.shaktipumps.shakti_rms.bean.AddDeviceBean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class AddDeviceResponse {

    @SerializedName("Active")
    private Boolean mActive;
    @SerializedName("DeviceNo")
    private String mDeviceNo;
    @SerializedName("DeviceStatus")
    private String mDeviceStatus;
    @SerializedName("IsLogin")
    private Boolean mIsLogin;
    @SerializedName("isvalid")
    private Boolean mIsvalid;
    @SerializedName("MDId")
    private String mMDId;
    @SerializedName("MDeviceId")
    private String mMDeviceId;
    @SerializedName("MUserId")
    private String mMUserId;
    @SerializedName("Mobile")
    private String mMobile;
    @SerializedName("MobileNo")
    private String mMobileNo;
    @SerializedName("ModelType")
    private String mModelType;
    @SerializedName("PlantId")
    private String mPlantId;
    @SerializedName("PumpStatus")
    private Boolean mPumpStatus;
    @SerializedName("Status")
    private Boolean mStatus;

    public Boolean getActive() {
        return mActive;
    }

    public void setActive(Boolean active) {
        mActive = active;
    }

    public String getDeviceNo() {
        return mDeviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        mDeviceNo = deviceNo;
    }

    public String getDeviceStatus() {
        return mDeviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        mDeviceStatus = deviceStatus;
    }

    public Boolean getIsLogin() {
        return mIsLogin;
    }

    public void setIsLogin(Boolean isLogin) {
        mIsLogin = isLogin;
    }

    public Boolean getIsvalid() {
        return mIsvalid;
    }

    public void setIsvalid(Boolean isvalid) {
        mIsvalid = isvalid;
    }

    public String getMDId() {
        return mMDId;
    }

    public void setMDId(String mDId) {
        mMDId = mDId;
    }

    public String getMDeviceId() {
        return mMDeviceId;
    }

    public void setMDeviceId(String mDeviceId) {
        mMDeviceId = mDeviceId;
    }

    public String getMUserId() {
        return mMUserId;
    }

    public void setMUserId(String mUserId) {
        mMUserId = mUserId;
    }

    public String getMobile() {
        return mMobile;
    }

    public void setMobile(String mobile) {
        mMobile = mobile;
    }

    public String getMobileNo() {
        return mMobileNo;
    }

    public void setMobileNo(String mobileNo) {
        mMobileNo = mobileNo;
    }

    public String getModelType() {
        return mModelType;
    }

    public void setModelType(String modelType) {
        mModelType = modelType;
    }

    public String getPlantId() {
        return mPlantId;
    }

    public void setPlantId(String plantId) {
        mPlantId = plantId;
    }

    public Boolean getPumpStatus() {
        return mPumpStatus;
    }

    public void setPumpStatus(Boolean pumpStatus) {
        mPumpStatus = pumpStatus;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
