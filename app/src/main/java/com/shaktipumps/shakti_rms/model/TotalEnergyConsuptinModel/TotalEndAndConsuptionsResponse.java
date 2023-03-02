
package com.shaktipumps.shakti_rms.model.TotalEnergyConsuptinModel;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class TotalEndAndConsuptionsResponse {

    @SerializedName("EnergyGeneration")
    private String mEnergyGeneration;

    @SerializedName("TotalFlow")
    private String mTotalFlow;

    @SerializedName("Energyconsumption")
    private String mEnergyconsumption;

    @SerializedName("TotalFault")
    private float mTotalFault;

    @SerializedName("FaultCount")
    private int mFaultCount;

    public float getTotalFault() {
        return mTotalFault;
    }

    public void setTotalFault(float totalFault) {
        mTotalFault = totalFault;
    }


    public String getTotalFlow() {
        return mTotalFlow;
    }

    public void setTotalFlow(String TotalFlow) {
        mTotalFlow = TotalFlow;
    }


    public int getFaultCount() {
        return mFaultCount;
    }

    public void setFaultCount(int faultCount) {
        mFaultCount = faultCount;
    }

    public String getEnergyGeneration() {
        return mEnergyGeneration;
    }

    public void setEnergyGeneration(String energyGeneration) {
        mEnergyGeneration = energyGeneration;
    }

    public String getEnergyconsumption() {
        return mEnergyconsumption;
    }

    public void setEnergyconsumption(String energyconsumption) {
        mEnergyconsumption = energyconsumption;
    }

}
