
package com.shaktipumps.shakti_rms.model.SettingModel;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class SettingModelResponse {

    @SerializedName("EditValue")
    private float mEditValue;

    @SerializedName("MobBTAddress")
    private String mMobBTAddress;


    @SerializedName("Address")
    private String mAddress;
    @SerializedName("Divisible")
    private String mDivisible;
    @SerializedName("MDeviceNo")
    private String mMDeviceNo;
    @SerializedName("MPId")
    private String mMPId;
    @SerializedName("MPIndex")
    private String mMPIndex;
    @SerializedName("MPName")
    private String mMPName;
    @SerializedName("Status")
    private String mStatus;
    @SerializedName("Unit")
    private String mUnit;

    @SerializedName("PMin")
    private int mPMin;

    @SerializedName("PMax")
    private int mPMax;

    @SerializedName("Offset")
    private int mOffset;

    public int getOffset() {
        return mOffset;
    }

    public void setOffset(int Offset) {
        mOffset = Offset;
    }



    public String getMobBTAddress() {
        return mMobBTAddress;
    }

    public void setMobBTAddress(String mobBTAddress) {
        mMobBTAddress = mobBTAddress;
    }



    public float getEditValue() {
        return mEditValue;
    }

    public void setEditValue(float editvalue) {
        mEditValue = editvalue;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getDivisible() {
        return mDivisible;
    }

    public void setDivisible(String divisible) {
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

    public String getMPIndex() {
        return mMPIndex;
    }

    public void setMPIndex(String mPIndex) {
        mMPIndex = mPIndex;
    }

    public String getMPName() {
        return mMPName;
    }

    public void setMPName(String mPName) {
        mMPName = mPName;
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

    public int getPMin() {
        return mPMin;
    }

    public void setPMin(int pmin) {
        mPMin = pmin;
    }


    public int getPMax() {
        return mPMax;
    }

    public void setPMax(int pmax) {
        mPMax = pmax;
    }





}
