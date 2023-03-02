
package com.shaktipumps.shakti_rms.bean.HomeOEMBean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class HomeDeviceOEResponse {

    @SerializedName("Client")
    private List<Client> mClient;
    @SerializedName("Device")
    private List<Device> mDevice;
    @SerializedName("PClient")
    private List<PClient> mPClient;

    public List<Client> getClient() {
        return mClient;
    }

    public void setClient(List<Client> client) {
        mClient = client;
    }

    public List<Device> getDevice() {
        return mDevice;
    }

    public void setDevice(List<Device> device) {
        mDevice = device;
    }

    public List<PClient> getPClient() {
        return mPClient;
    }

    public void setPClient(List<PClient> pClient) {
        mPClient = pClient;
    }

}
