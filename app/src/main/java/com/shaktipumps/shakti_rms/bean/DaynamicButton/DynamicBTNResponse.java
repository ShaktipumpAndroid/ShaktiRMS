
package com.shaktipumps.shakti_rms.bean.DaynamicButton;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DynamicBTNResponse {

    @SerializedName("address")
    private String mAddress;
    @SerializedName("bColor")
    private String mBColor;
    @SerializedName("buttonText")
    private String mButtonText;
    @SerializedName("data")
    private String mData;
    @SerializedName("deviceType")
    private String mDeviceType;
    @SerializedName("offset")
    private String mOffset;
    @SerializedName("sno")
    private String mSno;
    @SerializedName("btAddress")
    private String mbtAddress;
    @SerializedName("oldData")
    private String moldData;

    @SerializedName("cmdMsg")
    private String mcmdMsg;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getBColor() {
        return mBColor;
    }

    public void setBColor(String bColor) {
        mBColor = bColor;
    }

    public String getButtonText() {
        return mButtonText;
    }

    public void setButtonText(String buttonText) {
        mButtonText = buttonText;
    }

    public String getData() {
        return mData;
    }

    public void setData(String data) {
        mData = data;
    }

    public String getDeviceType() {
        return mDeviceType;
    }

    public void setDeviceType(String deviceType) {
        mDeviceType = deviceType;
    }

    public String getOffset() {
        return mOffset;
    }

    public void setOffset(String offset) {
        mOffset = offset;
    }

    public String getSno() {
        return mSno;
    }

    public void setSno(String sno) {
        mSno = sno;
    }

    public String getBTAddress() {
        return mbtAddress;
    }

    public void setBTAddress(String btAddress) {
        mbtAddress = btAddress;
    }

    public String getOldData() {
        return moldData;
    }

    public void setOldData(String oldData) {
        moldData = oldData;
    }

    public String getCmdMsg() {
        return mcmdMsg;
    }

    public void setCmdMsg(String cmdMsg) {
        mcmdMsg = cmdMsg;
    }

}
