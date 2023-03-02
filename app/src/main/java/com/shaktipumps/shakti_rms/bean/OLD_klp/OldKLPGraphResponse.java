
package com.shaktipumps.shakti_rms.bean.OLD_klp;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class OldKLPGraphResponse {

    @SerializedName("Energy")
    private float mEnergy;
    @SerializedName("Flow")
    private float mFlow;
    @SerializedName("MDate")
    private String mMDate;

    public float getEnergy() {
        return mEnergy;
    }

    public void setEnergy(float energy) {
        mEnergy = energy;
    }

    public float getFlow() {
        return mFlow;
    }

    public void setFlow(float flow) {
        mFlow = flow;
    }

    public String getMDate() {
        return mMDate;
    }

    public void setMDate(String mDate) {
        mMDate = mDate;
    }

}
