
package com.shaktipumps.shakti_rms.bean.GetPlant;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class KLPGRFMothtResponse {

    @SerializedName("edate")
    private String mEdate;
    @SerializedName("energy")
    private float mEnergy;

    public String getEdate() {
        return mEdate;
    }

    public void setEdate(String edate) {
        mEdate = edate;
    }

    public float getEnergy() {
        return mEnergy;
    }

    public void setEnergy(float energy) {
        mEnergy = energy;
    }

}
