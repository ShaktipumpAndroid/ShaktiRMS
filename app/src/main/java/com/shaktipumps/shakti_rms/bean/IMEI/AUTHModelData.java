
package com.shaktipumps.shakti_rms.bean.IMEI;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class AUTHModelData {

    @SerializedName("brokerUrl")
    private String mBrokerUrl;
    @SerializedName("clientId")
    private String mClientId;
    @SerializedName("infoTopic")
    private String mInfoTopic;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("username")
    private String mUsername;

    public String getBrokerUrl() {
        return mBrokerUrl;
    }

    public void setBrokerUrl(String brokerUrl) {
        mBrokerUrl = brokerUrl;
    }

    public String getClientId() {
        return mClientId;
    }

    public void setClientId(String clientId) {
        mClientId = clientId;
    }

    public String getInfoTopic() {
        return mInfoTopic;
    }

    public void setInfoTopic(String infoTopic) {
        mInfoTopic = infoTopic;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

}
