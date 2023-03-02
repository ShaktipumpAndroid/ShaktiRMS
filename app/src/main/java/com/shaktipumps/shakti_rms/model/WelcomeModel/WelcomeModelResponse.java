
package com.shaktipumps.shakti_rms.model.WelcomeModel;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class WelcomeModelResponse {

    @SerializedName("ImgUrl")
    private String mImgUrl;
    @SerializedName("layerText")
    private String mLayerText;
    @SerializedName("Sno")
    private Long mSno;

    public String getImgUrl() {
        return mImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        mImgUrl = imgUrl;
    }

    public String getLayerText() {
        return mLayerText;
    }

    public void setLayerText(String layerText) {
        mLayerText = layerText;
    }

    public Long getSno() {
        return mSno;
    }

    public void setSno(Long sno) {
        mSno = sno;
    }

}
