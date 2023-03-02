
package com.shaktipumps.shakti_rms.bean.userDeviceCheck;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class UserCheckDeviceResponse {

    @SerializedName("Device")
    private int mDevice;
    @SerializedName("PlantId")
    private int mPlantId;

    public int getDevice() {
        return mDevice;
    }

    public void setDevice(int device) {
        mDevice = device;
    }

    public int getPlantId() {
        return mPlantId;
    }

    public void setPlantId(int plantId) {
        mPlantId = plantId;
    }

}
