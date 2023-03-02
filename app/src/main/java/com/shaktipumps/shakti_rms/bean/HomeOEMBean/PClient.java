
package com.shaktipumps.shakti_rms.bean.HomeOEMBean;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class PClient {

    @SerializedName("ClientId")
    private String mClientId;
    @SerializedName("ParentId")
    private String mParentId;
    @SerializedName("SupParentId")
    private String mSupParentId;

    public String getClientId() {
        return mClientId;
    }

    public void setClientId(String clientId) {
        mClientId = clientId;
    }

    public String getParentId() {
        return mParentId;
    }

    public void setParentId(String parentId) {
        mParentId = parentId;
    }

    public String getSupParentId() {
        return mSupParentId;
    }

    public void setSupParentId(String supParentId) {
        mSupParentId = supParentId;
    }

}
