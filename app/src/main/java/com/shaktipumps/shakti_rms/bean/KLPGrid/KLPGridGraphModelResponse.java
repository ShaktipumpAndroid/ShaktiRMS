
package com.shaktipumps.shakti_rms.bean.KLPGrid;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class KLPGridGraphModelResponse {

    @SerializedName("MDate")
    private String mMDate;
    @SerializedName("TOTALGSCEnergy")
    private float mTOTALGSCEnergy;

    public String getMDate() {
        return mMDate;
    }

    public void setMDate(String mDate) {
        mMDate = mDate;
    }

    public float getTOTALGSCEnergy() {
        return mTOTALGSCEnergy;
    }

    public void setTOTALGSCEnergy(float tOTALGSCEnergy) {
        mTOTALGSCEnergy = tOTALGSCEnergy;
    }

}
