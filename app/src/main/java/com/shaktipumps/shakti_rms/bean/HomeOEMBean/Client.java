
package com.shaktipumps.shakti_rms.bean.HomeOEMBean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Client {

    @SerializedName("ClientId")
    private String mClientId;
    @SerializedName("ClientName")
    private String mClientName;
    @SerializedName("ParentId")
    private String mParentId;
    @SerializedName("ParentName")
    private String mParentName;
    @SerializedName("SupParentId")
    private String mSupParentId;

    public String getClientId() {
        return mClientId;
    }

    public void setClientId(String clientId) {
        mClientId = clientId;
    }

    public String getClientName() {
        return mClientName;
    }

    public void setClientName(String clientName) {
        mClientName = clientName;
    }

    public String getParentId() {
        return mParentId;
    }

    public void setParentId(String parentId) {
        mParentId = parentId;
    }

    public String getParentName() {
        return mParentName;
    }

    public void setParentName(String parentName) {
        mParentName = parentName;
    }

    public String getSupParentId() {
        return mSupParentId;
    }

    public void setSupParentId(String supParentId) {
        mSupParentId = supParentId;
    }

}
