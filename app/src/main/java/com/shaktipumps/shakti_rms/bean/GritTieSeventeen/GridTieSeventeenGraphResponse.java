
package com.shaktipumps.shakti_rms.bean.GritTieSeventeen;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class GridTieSeventeenGraphResponse {


    @SerializedName("MDate")
    private String mMDate;
    @SerializedName("TotalGridEnergy")
    private float mTotalGridEnergy;


    public String getMDate() {
        return mMDate;
    }

    public void setMDate(String mDate) {
        mMDate = mDate;
    }

    public float getTotalGridEnergy() {
        return mTotalGridEnergy;
    }

    public void setTotalGridEnergy(float totalGridEnergy) {
        mTotalGridEnergy = totalGridEnergy;
    }

}
