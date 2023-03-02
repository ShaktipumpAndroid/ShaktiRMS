
package com.shaktipumps.shakti_rms.bean.KLPBean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class KLPGraphResponse {

    @SerializedName("MDate")
    private String mMDate;
    @SerializedName("TOTALENERGYVFD")
    private float mTOTALENERGYVFD;
    @SerializedName("TOTALGSCEnergy")
    private float mTOTALGSCEnergy;
    @SerializedName("TOTALFLOW")
    private float mTOTALFLOW;

    public String getMDate() {
        return mMDate;
    }

    public void setMDate(String mDate) {
        mMDate = mDate;
    }

    public float getTOTALENERGYVFD() {
        return mTOTALENERGYVFD;
    }

    public void setTOTALENERGYVFD(float tOTALENERGYVFD) {
        mTOTALENERGYVFD = tOTALENERGYVFD;
    }

    public float getTOTALGSCEnergy() {
        return mTOTALGSCEnergy;
    }

    public void setTOTALGSCEnergy(float tOTALGSCEnergy) {
        mTOTALGSCEnergy = tOTALGSCEnergy;
    }


    public float getTOTALFLOW() {
        return mTOTALFLOW;
    }

    public void setTOTALFLOW(float mmTOTALFLOW) {
        mTOTALFLOW = mmTOTALFLOW;
    }

}
