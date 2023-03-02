
package com.shaktipumps.shakti_rms.bean.PaymentBean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PaymentPlanResponse {

    @SerializedName("MDeviceId")
    private String mMDeviceId;
    @SerializedName("MUserId")
    private String mMUserId;
    @SerializedName("PaymentStatus")
    private String mPaymentStatus;
    @SerializedName("PlanAmount")
    private String mPlanAmount;
    @SerializedName("PlanDescription")
    private String mPlanDescription;
    @SerializedName("PlanDuration")
    private String mPlanDuration;
    @SerializedName("PlanId")
    private String mPlanId;
    @SerializedName("Status")
    private Boolean mStatus;
    @SerializedName("USubID")
    private String mUSubID;
    @SerializedName("Valid")
    private String mValid;
    @SerializedName("ValidTo")
    private String mValidTo;

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

    public String getPaymentStatus() {
        return mPaymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        mPaymentStatus = paymentStatus;
    }

    public String getPlanAmount() {
        return mPlanAmount;
    }

    public void setPlanAmount(String planAmount) {
        mPlanAmount = planAmount;
    }

    public String getPlanDescription() {
        return mPlanDescription;
    }

    public void setPlanDescription(String planDescription) {
        mPlanDescription = planDescription;
    }

    public String getPlanDuration() {
        return mPlanDuration;
    }

    public void setPlanDuration(String planDuration) {
        mPlanDuration = planDuration;
    }

    public String getPlanId() {
        return mPlanId;
    }

    public void setPlanId(String planId) {
        mPlanId = planId;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

    public String getUSubID() {
        return mUSubID;
    }

    public void setUSubID(String uSubID) {
        mUSubID = uSubID;
    }

    public String getValid() {
        return mValid;
    }

    public void setValid(String valid) {
        mValid = valid;
    }

    public String getValidTo() {
        return mValidTo;
    }

    public void setValidTo(String validTo) {
        mValidTo = validTo;
    }

}
