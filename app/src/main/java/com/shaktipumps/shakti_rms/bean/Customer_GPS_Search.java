package com.shaktipumps.shakti_rms.bean;

import java.io.Serializable;

/**
 * Created by shakti on 19-Apr-18.
 */
public class Customer_GPS_Search implements Serializable {

    String customer_name = "null",
            DeviceNo = "null",
            DeviceType = "null",
            MDeviceId = "null",
            MUserId = "null",
            Mobile = "null",
            MobValidationDate = "null",
            TypeName = "null",//TypeName
            PumpStatus = "null",
            DeviceImage = "null",
            IsLogin = "null",

            ModelType = "null";

    int  DeviceStatus ;


    public String getDeviceImage() {
        return DeviceImage;
    }

    public void setDeviceImage(String DeviceImage) {
        this.DeviceImage = DeviceImage;
    }


    public String getIsLogin() {
        return IsLogin;
    }

    public void setIsLogin(String IsLogin) {
        this.IsLogin = IsLogin;
    }


    public String getPumpStatus() {
        return PumpStatus;
    }

    public void setPumpStatus(String PumpStatus) {
        this.PumpStatus = PumpStatus;
    }


    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String TypeName) {
        this.TypeName = TypeName;
    }


    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getDeviceNo() {
        return DeviceNo;
    }

    public void setDeviceNo(String DeviceNo) {
        this.DeviceNo = DeviceNo;
    }

    public String getDeviceType() {
        return DeviceType;
    }

    public void setDeviceType(String deviceType) {
        DeviceType = deviceType;
    }

    public String getMDeviceId() {
        return MDeviceId;
    }

    public void setMDeviceId(String MDeviceId) {
        this.MDeviceId = MDeviceId;
    }

    public String getMUserId() {
        return MUserId;
    }

    public void setMUserId(String MUserId) {
        this.MUserId = MUserId;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getMobValidationDate() {
        return MobValidationDate;
    }

    public void setMobValidationDate(String mMobValidationDate) {
        MobValidationDate = mMobValidationDate;
    }


    public String getModelType() {
        return ModelType;
    }

    public void setModelType(String modelType) {
        ModelType = modelType;
    }

    public int getDeviceStatus() {
        return DeviceStatus;
    }

    public void setDeviceStatus(int mDeviceStatus) {
        DeviceStatus = mDeviceStatus;
    }
}
