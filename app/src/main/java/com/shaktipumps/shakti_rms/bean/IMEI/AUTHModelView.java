
package com.shaktipumps.shakti_rms.bean.IMEI;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class AUTHModelView {

    @SerializedName("data")
    private AUTHModelData mData;
    @SerializedName("result")
    private AUTHModelResult mResult;

    public AUTHModelData getData() {
        return mData;
    }

    public void setData(AUTHModelData data) {
        mData = data;
    }

    public AUTHModelResult getResult() {
        return mResult;
    }

    public void setResult(AUTHModelResult result) {
        mResult = result;
    }

}
