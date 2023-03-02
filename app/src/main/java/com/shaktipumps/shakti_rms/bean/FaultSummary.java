package com.shaktipumps.shakti_rms.bean;

/**
 * Created by shakti on 19-May-18.
 */
public class FaultSummary {
  String
            MUserId = "null",
            DeviceNo = "null",
            FalutCount = "null",
            FaultBit     = "null" ,
          DeviceType = "null",
            Date = "null";



    public String getMUserId() {
        return MUserId;
    }

    public void setMUserId(String MUserId) {
        this.MUserId = MUserId;
    }

    public String getDeviceNo() {
        return DeviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        DeviceNo = deviceNo;
    }

    public String getFalutCount() {
        return FalutCount;
    }

    public void setFalutCount(String falutCount) {
        FalutCount = falutCount;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }


    public String getFaultBit() {
        return FaultBit;
    }

    public void setFaultBit(String faultBit) {
        FaultBit = faultBit;
    }

    public String getDeviceType() {
        return DeviceType;
    }

    public void setDeviceType(String deviceType) {
        DeviceType = deviceType;
    }
}
