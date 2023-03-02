package com.shaktipumps.shakti_rms.bean;

/**
 * Created by shakti on 27-Jun-18.
 */
public class Org_Client {
    String ClientId = "null",
            ParentId = "null",
            ParentName = "null",
            ClientName = "null",
            TypeName = "null",
            PumpStatus = "null",
            DeviceImage = "null",
            IsLogin = "null",
            ModelType = "null";



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

    ///above new parameter

    public String getClientId() {
        return ClientId;
    }

    public void setClientId(String clientId) {
        ClientId = clientId;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public String getParentName() {
        return ParentName;
    }

    public void setParentName(String parentName) {
        ParentName = parentName;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }
}
