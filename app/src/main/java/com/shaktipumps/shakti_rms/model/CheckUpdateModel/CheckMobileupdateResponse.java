
package com.shaktipumps.shakti_rms.model.CheckUpdateModel;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CheckMobileupdateResponse {

    @SerializedName("MobVersion")
    private Float mMobVersion;

    public Float getMobVersion() {
        return mMobVersion;
    }

    public void setMobVersion(Float mobVersion) {
        mMobVersion = mobVersion;
    }

}
