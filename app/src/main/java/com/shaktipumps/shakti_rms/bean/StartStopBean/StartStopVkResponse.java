
package com.shaktipumps.shakti_rms.bean.StartStopBean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class StartStopVkResponse {

    @SerializedName("CEnergyF")
    private String mCEnergyF;
    @SerializedName("CFlowF")
    private String mCFlowF;
    @SerializedName("CTimeF")
    private String mCTimeF;
    @SerializedName("energy")
    private String mEnergy;
    @SerializedName("flow")
    private String mFlow;
    @SerializedName("Latitude")
    private String mLatitude;
    @SerializedName("Longitude")
    private String mLongitude;
    @SerializedName("MId")
    private String mMId;
    @SerializedName("Result")
    private String mResult;
    @SerializedName("time")
    private String mTime;
    @SerializedName("valid")
    private Boolean mValid;

    public String getCEnergyF() {
        return mCEnergyF;
    }

    public void setCEnergyF(String cEnergyF) {
        mCEnergyF = cEnergyF;
    }

    public String getCFlowF() {
        return mCFlowF;
    }

    public void setCFlowF(String cFlowF) {
        mCFlowF = cFlowF;
    }

    public String getCTimeF() {
        return mCTimeF;
    }

    public void setCTimeF(String cTimeF) {
        mCTimeF = cTimeF;
    }

    public String getEnergy() {
        return mEnergy;
    }

    public void setEnergy(String energy) {
        mEnergy = energy;
    }

    public String getFlow() {
        return mFlow;
    }

    public void setFlow(String flow) {
        mFlow = flow;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String latitude) {
        mLatitude = latitude;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public void setLongitude(String longitude) {
        mLongitude = longitude;
    }

    public String getMId() {
        return mMId;
    }

    public void setMId(String mId) {
        mMId = mId;
    }

    public String getResult() {
        return mResult;
    }

    public void setResult(String result) {
        mResult = result;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public Boolean getValid() {
        return mValid;
    }

    public void setValid(Boolean valid) {
        mValid = valid;
    }

}
