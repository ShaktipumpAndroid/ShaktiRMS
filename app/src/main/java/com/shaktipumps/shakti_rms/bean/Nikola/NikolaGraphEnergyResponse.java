
package com.shaktipumps.shakti_rms.bean.Nikola;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class NikolaGraphEnergyResponse {

    @SerializedName("TOTALENERGYLOAD")
    private float mTOTALENERGYLOAD;
    @SerializedName("TOTALGRIDEnergy")
    private float mTOTALGRIDEnergy;
    @SerializedName("MDate")
    private String mMDate;

    public float getTOTALENERGYLOAD() {
        return mTOTALENERGYLOAD;
    }

    public void setTOTALENERGYLOAD(float tOTALENERGYLOAD) {
        mTOTALENERGYLOAD = tOTALENERGYLOAD;
    }

    public float getTOTALGRIDEnergy() {
        return mTOTALGRIDEnergy;
    }

    public void setTOTALGRIDEnergy(float tOTALGRIDEnergy) {
        mTOTALGRIDEnergy = tOTALGRIDEnergy;
    }


    public String getMDate() {
        return mMDate;
    }

    public void setMDate(String MDatee) {
        mMDate = MDatee;
    }

}
