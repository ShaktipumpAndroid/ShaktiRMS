
package com.shaktipumps.shakti_rms.bean.KlpGridDashboardBean;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class KlpgridDashboardResponse {

    @SerializedName("tgridpower")
    private String mTgridpower;
   // @SerializedName("todaygscenergy")
    @SerializedName("powerfeeding")
    private String mPowerfeeding;
    @SerializedName("todaygschrs")
    private String mTodaygschrs;
   // @SerializedName("todaytotalgscenergy")
    @SerializedName("todaytotalgscenergy")
    private String mTodaytotalgscenergy;
    @SerializedName("totalgschrs")
    private String mTotalgschrs;
    @SerializedName("devicecount")
    private String mDevicecount;

    @SerializedName("totalDevices")
    private String mTotalDevices;

    @SerializedName("Offlinedevice")
    private String mOfflinedevice;

    @SerializedName("fault")
    private String mFault;

    @SerializedName("Lat")
    private String mLat;

    @SerializedName("Long")
    private String mLong;

    public String getOfflinedevice() {
        return mOfflinedevice;
    }

    public void setOfflinedevice(String offlinedevice) {
        mOfflinedevice = offlinedevice;
    }

    public String getTotalDevices() {
        return mTotalDevices;
    }

    public void setTotalDevices(String totalDevices) {
        mTotalDevices = totalDevices;
    }

    public String getFault() {
        return mFault;
    }

    public void setFault(String fault) {
        mFault = fault;
    }


    public String getLat() {
        return mLat;
    }

    public void setLat(String Lat) {
        mLat = Lat;
    }

    public String getLong() {
        return mLong;
    }

    public void setLong(String Long) {
        mLong = Long;
    }

    public String getDevicecount() {
        return mDevicecount;
    }

    public void setDevicecount(String devicecount) {
        mDevicecount = devicecount;
    }

    public String getTgridpower() {
        return mTgridpower;
    }

    public void setTgridpower(String tgridpower) {
        mTgridpower = tgridpower;
    }

    public String getPowerfeeding() {
        return mPowerfeeding;
    }

    public void setPowerfeeding(String powerfeeding) {
        mPowerfeeding = powerfeeding;
    }

    public String getTodaygschrs() {
        return mTodaygschrs;
    }

    public void setTodaygschrs(String todaygschrs) {
        mTodaygschrs = todaygschrs;
    }

    public String getTodaytotalgscenergy() {
        return mTodaytotalgscenergy;
    }

    public void setTodaytotalgscenergy(String todaytotalgscenergy) {
        mTodaytotalgscenergy = todaytotalgscenergy;
    }

    public String getTotalgschrs() {
        return mTotalgschrs;
    }

    public void setTotalgschrs(String totalgschrs) {
        mTotalgschrs = totalgschrs;
    }

}
