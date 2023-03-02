
package com.shaktipumps.shakti_rms.bean.RealMonitorBean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RealMonitorParameterResponse {

    @SerializedName("Address")
    private String mAddress;
    @SerializedName("DeviceTypeId")
    private String mDeviceTypeId;
    @SerializedName("DisplayIndex")
    private String mDisplayIndex;
    @SerializedName("Divisible")
    private float mDivisible;
    @SerializedName("MDeviceNo")
    private String mMDeviceNo;
    @SerializedName("MPId")
    private String mMPId;
    @SerializedName("MPIndex")
    private int mMPIndex;
    @SerializedName("MPName")
    private String mMPName;
    @SerializedName("MStatus")
    private Boolean mMStatus;
    @SerializedName("MTypeId")
    private String mMTypeId;
    @SerializedName("PMax")
    private String mPMax;
    @SerializedName("PMin")
    private String mPMin;
    @SerializedName("Status")
    private String mStatus;
    @SerializedName("Unit")
    private String mUnit;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getDeviceTypeId() {
        return mDeviceTypeId;
    }

    public void setDeviceTypeId(String deviceTypeId) {
        mDeviceTypeId = deviceTypeId;
    }

    public String getDisplayIndex() {
        return mDisplayIndex;
    }

    public void setDisplayIndex(String displayIndex) {
        mDisplayIndex = displayIndex;
    }

    public float getDivisible() {
        return mDivisible;
    }

    public void setDivisible(float divisible) {
        mDivisible = divisible;
    }

    public String getMDeviceNo() {
        return mMDeviceNo;
    }

    public void setMDeviceNo(String mDeviceNo) {
        mMDeviceNo = mDeviceNo;
    }

    public String getMPId() {
        return mMPId;
    }

    public void setMPId(String mPId) {
        mMPId = mPId;
    }

    public int getMPIndex() {
        return mMPIndex;
    }

    public void setMPIndex(int mPIndex) {
        mMPIndex = mPIndex;
    }

    public String getMPName() {
        return mMPName;
    }

    public void setMPName(String mPName) {
        mMPName = mPName;
    }

    public Boolean getMStatus() {
        return mMStatus;
    }

    public void setMStatus(Boolean mStatus) {
        mMStatus = mStatus;
    }

    public String getMTypeId() {
        return mMTypeId;
    }

    public void setMTypeId(String mTypeId) {
        mMTypeId = mTypeId;
    }

    public String getPMax() {
        return mPMax;
    }

    public void setPMax(String pMax) {
        mPMax = pMax;
    }

    public String getPMin() {
        return mPMin;
    }

    public void setPMin(String pMin) {
        mPMin = pMin;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getUnit() {
        return mUnit;
    }

    public void setUnit(String unit) {
        mUnit = unit;
    }

}
