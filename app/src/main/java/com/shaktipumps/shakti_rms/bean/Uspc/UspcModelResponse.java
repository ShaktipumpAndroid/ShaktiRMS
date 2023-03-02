
package com.shaktipumps.shakti_rms.bean.Uspc;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class UspcModelResponse {

    @SerializedName("bgColor")
    private String mBgColor;
    @SerializedName("id")
    private String mId;
    @SerializedName("image")
    private String mImage;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("title")
    private String mTitle;

    @SerializedName("displayIndex")
    private String mDisplayIndex;

    @SerializedName("address")
    private String mAddress;

    @SerializedName("btaddress")
    private String mBTaddress;

    public String getBgColor() {
        return mBgColor;
    }

    public void setBgColor(String bgColor) {
        mBgColor = bgColor;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDisplayIndex() {
        return mDisplayIndex;
    }

    public void setDisplayIndex(String displayIndex) {
        mDisplayIndex = displayIndex;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }


    public String getBTaddress() {
        return mBTaddress;
    }

    public void setBTaddress(String btaddress) {
        mBTaddress = btaddress;
    }
}
