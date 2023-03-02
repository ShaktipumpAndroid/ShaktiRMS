
package com.shaktipumps.shakti_rms.model.EnergyGraphModel;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class EnergyGraphEnergy {

    @SerializedName("CEnergyF")
    private float mCEnergyF;
    @SerializedName("CFlowF")
    private float mCFlowF;
    @SerializedName("CTimeF")
    private Long mCTimeF;
    @SerializedName("EDATE")
    private String mEDATE;

    public float getCEnergyF() {
        return mCEnergyF;
    }

    public void setCEnergyF(float cEnergyF) {
        mCEnergyF = cEnergyF;
    }

    public float getCFlowF() {
        return mCFlowF;
    }

    public void setCFlowF(float cFlowF) {
        mCFlowF = cFlowF;
    }

    public Long getCTimeF() {
        return mCTimeF;
    }

    public void setCTimeF(Long cTimeF) {
        mCTimeF = cTimeF;
    }

    public String getEDATE() {
        return mEDATE;
    }

    public void setEDATE(String eDATE) {
        mEDATE = eDATE;
    }

}
