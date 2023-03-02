
package com.shaktipumps.shakti_rms.bean.ForgetOTPBean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ForgotOTPPassResponse {

    @SerializedName("Active")
    private Boolean mActive;
    @SerializedName("ClientName")
    private String mClientName;
    @SerializedName("IsExist")
    private Boolean mIsExist;
    @SerializedName("IsLogin")
    private Boolean mIsLogin;
    @SerializedName("MParentId")
    private String mMParentId;
    @SerializedName("MUserId")
    private String mMUserId;
    @SerializedName("MUserName")
    private String mMUserName;
    @SerializedName("MobileNo")
    private String mMobileNo;
    @SerializedName("mvId")
    private String mMvId;
    @SerializedName("OTP")
    private String mOTP;
    @SerializedName("Status")
    private Boolean mStatus;

    public Boolean getActive() {
        return mActive;
    }

    public void setActive(Boolean active) {
        mActive = active;
    }

    public String getClientName() {
        return mClientName;
    }

    public void setClientName(String clientName) {
        mClientName = clientName;
    }

    public Boolean getIsExist() {
        return mIsExist;
    }

    public void setIsExist(Boolean isExist) {
        mIsExist = isExist;
    }

    public Boolean getIsLogin() {
        return mIsLogin;
    }

    public void setIsLogin(Boolean isLogin) {
        mIsLogin = isLogin;
    }

    public String getMParentId() {
        return mMParentId;
    }

    public void setMParentId(String mParentId) {
        mMParentId = mParentId;
    }

    public String getMUserId() {
        return mMUserId;
    }

    public void setMUserId(String mUserId) {
        mMUserId = mUserId;
    }

    public String getMUserName() {
        return mMUserName;
    }

    public void setMUserName(String mUserName) {
        mMUserName = mUserName;
    }

    public String getMobileNo() {
        return mMobileNo;
    }

    public void setMobileNo(String mobileNo) {
        mMobileNo = mobileNo;
    }

    public String getMvId() {
        return mMvId;
    }

    public void setMvId(String mvId) {
        mMvId = mvId;
    }

    public String getOTP() {
        return mOTP;
    }

    public void setOTP(String oTP) {
        mOTP = oTP;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
