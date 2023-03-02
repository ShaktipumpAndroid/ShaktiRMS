
package com.shaktipumps.shakti_rms.bean.GetPlant;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class KLPGRFDayResponse {

    @SerializedName("energy")
    private float mEnergy;
    @SerializedName("hrs")
    private String mHrs;

    public float getEnergy() {
        return mEnergy;
    }

    public void setEnergy(float energy) {
        mEnergy = energy;
    }

    public String getHrs() {
        return mHrs;
    }

    public void setHrs(String hrs) {
        mHrs = hrs;
    }

}
