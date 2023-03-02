
package com.shaktipumps.shakti_rms.bean.GetPlant;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class FaultRecordResponse {
    @SerializedName("deviceNo")
    private String mDeviceNo;
    @SerializedName("faultDate")
    private String mFaultDate;
    @SerializedName("faultName")
    private String mFaultName;
    @SerializedName("faultTime")
    private String mFaultTime;
    @SerializedName("plantId")
    private Long mPlantId;

    public String getDeviceNo() {
        return mDeviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        mDeviceNo = deviceNo;
    }

    public String getFaultDate() {
        return mFaultDate;
    }

    public void setFaultDate(String faultDate) {
        mFaultDate = faultDate;
    }

    public String getFaultName() {
        return mFaultName;
    }

    public void setFaultName(String faultName) {
        mFaultName = faultName;
    }

    public String getFaultTime() {
        return mFaultTime;
    }

    public void setFaultTime(String faultTime) {
        mFaultTime = faultTime;
    }

    public Long getPlantId() {
        return mPlantId;
    }

    public void setPlantId(Long plantId) {
        mPlantId = plantId;
    }

}
