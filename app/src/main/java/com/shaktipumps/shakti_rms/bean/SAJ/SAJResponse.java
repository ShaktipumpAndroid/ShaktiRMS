
package com.shaktipumps.shakti_rms.bean.SAJ;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SAJResponse {

    @SerializedName("Fault")
    private int mFault;
    @SerializedName("TDGEnergyName")
    private String mTDGEnergyName;
    @SerializedName("TDGEnergyUnit")
    private String mTDGEnergyUnit;
    @SerializedName("TDGEnergyValue")
    private String mTDGEnergyValue;
    @SerializedName("TDGRHName")
    private String mTDGRHName;
    @SerializedName("TDGRHValue")
    private String mTDGRHValue;
    @SerializedName("TDGRHrUnit")
    private String mTDGRHrUnit;
    @SerializedName("TOTGEnergyName")
    private String mTOTGEnergyName;
    @SerializedName("TOTGEnergyUnit")
    private String mTOTGEnergyUnit;
    @SerializedName("TOTGEnergyValue")
    private String mTOTGEnergyValue;
    @SerializedName("TOTGRHName")
    private String mTOTGRHName;
    @SerializedName("TOTGRHUnit")
    private String mTOTGRHUnit;
    @SerializedName("TOTGRHValue")
    private String mTOTGRHValue;
    @SerializedName("TotalFault")
    private int mTotalFault;

    public int getFault() {
        return mFault;
    }

    public void setFault(int fault) {
        mFault = fault;
    }

    public String getTDGEnergyName() {
        return mTDGEnergyName;
    }

    public void setTDGEnergyName(String tDGEnergyName) {
        mTDGEnergyName = tDGEnergyName;
    }

    public String getTDGEnergyUnit() {
        return mTDGEnergyUnit;
    }

    public void setTDGEnergyUnit(String tDGEnergyUnit) {
        mTDGEnergyUnit = tDGEnergyUnit;
    }

    public String getTDGEnergyValue() {
        return mTDGEnergyValue;
    }

    public void setTDGEnergyValue(String tDGEnergyValue) {
        mTDGEnergyValue = tDGEnergyValue;
    }

    public String getTDGRHName() {
        return mTDGRHName;
    }

    public void setTDGRHName(String tDGRHName) {
        mTDGRHName = tDGRHName;
    }

    public String getTDGRHValue() {
        return mTDGRHValue;
    }

    public void setTDGRHValue(String tDGRHValue) {
        mTDGRHValue = tDGRHValue;
    }

    public String getTDGRHrUnit() {
        return mTDGRHrUnit;
    }

    public void setTDGRHrUnit(String tDGRHrUnit) {
        mTDGRHrUnit = tDGRHrUnit;
    }

    public String getTOTGEnergyName() {
        return mTOTGEnergyName;
    }

    public void setTOTGEnergyName(String tOTGEnergyName) {
        mTOTGEnergyName = tOTGEnergyName;
    }

    public String getTOTGEnergyUnit() {
        return mTOTGEnergyUnit;
    }

    public void setTOTGEnergyUnit(String tOTGEnergyUnit) {
        mTOTGEnergyUnit = tOTGEnergyUnit;
    }

    public String getTOTGEnergyValue() {
        return mTOTGEnergyValue;
    }

    public void setTOTGEnergyValue(String tOTGEnergyValue) {
        mTOTGEnergyValue = tOTGEnergyValue;
    }

    public String getTOTGRHName() {
        return mTOTGRHName;
    }

    public void setTOTGRHName(String tOTGRHName) {
        mTOTGRHName = tOTGRHName;
    }

    public String getTOTGRHUnit() {
        return mTOTGRHUnit;
    }

    public void setTOTGRHUnit(String tOTGRHUnit) {
        mTOTGRHUnit = tOTGRHUnit;
    }

    public String getTOTGRHValue() {
        return mTOTGRHValue;
    }

    public void setTOTGRHValue(String tOTGRHValue) {
        mTOTGRHValue = tOTGRHValue;
    }

    public int getTotalFault() {
        return mTotalFault;
    }

    public void setTotalFault(int totalFault) {
        mTotalFault = totalFault;
    }

}
