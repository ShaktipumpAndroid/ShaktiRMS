
package com.shaktipumps.shakti_rms.bean.GetPlant;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class KLPOnlyMonthEnergyResponse {

    @SerializedName("totalMonthEnergy")
    private String mTotalMonthEnergy;

    public String getTotalMonthEnergy() {
        return mTotalMonthEnergy;
    }

    public void setTotalMonthEnergy(String totalMonthEnergy) {
        mTotalMonthEnergy = totalMonthEnergy;
    }

}
