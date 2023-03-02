
package com.shaktipumps.shakti_rms.bean.IMEI;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class AUTHModelResult {

    @SerializedName("Flag")
    private Boolean mFlag;
    @SerializedName("Message")
    private String mMessage;

    public Boolean getFlag() {
        return mFlag;
    }

    public void setFlag(Boolean flag) {
        mFlag = flag;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

}
