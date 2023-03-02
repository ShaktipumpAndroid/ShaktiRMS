
package com.shaktipumps.shakti_rms.model.ForgotOTPModel;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ForgotPassResponse {

    @SerializedName("OTP")
    private String mOTP;

    public String getOTP() {
        return mOTP;
    }

    public void setOTP(String oTP) {
        mOTP = oTP;
    }

}
