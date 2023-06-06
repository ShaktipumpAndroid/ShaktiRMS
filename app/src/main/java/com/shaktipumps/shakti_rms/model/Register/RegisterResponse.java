package com.shaktipumps.shakti_rms.model.Register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

    @SerializedName("MUserId")
    @Expose
    private Integer mUserId;
    @SerializedName("MParentId")
    @Expose
    private Integer mParentId;
    @SerializedName("ClientName")
    @Expose
    private Integer clientName;
    @SerializedName("MFirstName")
    @Expose
    private String mFirstName;
    @SerializedName("MLastName")
    @Expose
    private String mLastName;
    @SerializedName("MUserName")
    @Expose
    private String mUserName;
    @SerializedName("MPassword")
    @Expose
    private String mPassword;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("Active")
    @Expose
    private Boolean active;
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("IsLogin")
    @Expose
    private Boolean isLogin;
    @SerializedName("MAddress")
    @Expose
    private String mAddress;
    @SerializedName("IsExist")
    @Expose
    private Boolean isExist;
    @SerializedName("mvId")
    @Expose
    private Integer mvId;

    public Integer getMUserId() {
        return mUserId;
    }

    public void setMUserId(Integer mUserId) {
        this.mUserId = mUserId;
    }

    public Integer getMParentId() {
        return mParentId;
    }

    public void setMParentId(Integer mParentId) {
        this.mParentId = mParentId;
    }

    public Integer getClientName() {
        return clientName;
    }

    public void setClientName(Integer clientName) {
        this.clientName = clientName;
    }

    public String getMFirstName() {
        return mFirstName;
    }

    public void setMFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getMLastName() {
        return mLastName;
    }

    public void setMLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getMUserName() {
        return mUserName;
    }

    public void setMUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getMPassword() {
        return mPassword;
    }

    public void setMPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Boolean isLogin) {
        this.isLogin = isLogin;
    }

    public String getMAddress() {
        return mAddress;
    }

    public void setMAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public Boolean getIsExist() {
        return isExist;
    }

    public void setIsExist(Boolean isExist) {
        this.isExist = isExist;
    }

    public Integer getMvId() {
        return mvId;
    }

    public void setMvId(Integer mvId) {
        this.mvId = mvId;
    }

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "mUserId=" + mUserId +
                ", mParentId=" + mParentId +
                ", clientName=" + clientName +
                ", mFirstName='" + mFirstName + '\'' +
                ", mLastName='" + mLastName + '\'' +
                ", mUserName='" + mUserName + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", active=" + active +
                ", status=" + status +
                ", isLogin=" + isLogin +
                ", mAddress='" + mAddress + '\'' +
                ", isExist=" + isExist +
                ", mvId=" + mvId +
                '}';
    }
}

