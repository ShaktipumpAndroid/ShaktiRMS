package com.shaktipumps.shakti_rms.localdbmodel;

import java.io.Serializable;

public class userDbLoginModel implements Serializable {

    String mLoginID = null;
    String mUserID = null;
    String mParentID = null;
    String mClientID = null;
    String mUSername = null;
    String mUserPhone = null;
    String mUserIsLogin = null;
    String mUserStatus = null;

    public String getmLoginID() {
        return mLoginID;
    }

    public void setmLoginID(String mLoginID) {
        this.mLoginID = mLoginID;
    }

    public String getmUserID() {
        return mUserID;
    }

    public void setmUserID(String mUserID) {
        this.mUserID = mUserID;
    }

    public String getmParentID() {
        return mParentID;
    }

    public void setmParentID(String mParentID) {
        this.mParentID = mParentID;
    }

    public String getmClientID() {
        return mClientID;
    }

    public void setmClientID(String mClientID) {
        this.mClientID = mClientID;
    }

    public String getmUSername() {
        return mUSername;
    }

    public void setmUSername(String mUSername) {
        this.mUSername = mUSername;
    }

    public String getmUserPhone() {
        return mUserPhone;
    }

    public void setmUserPhone(String mUserPhone) {
        this.mUserPhone = mUserPhone;
    }

    public String getmUserIsLogin() {
        return mUserIsLogin;
    }

    public void setmUserIsLogin(String mUserIsLogin) {
        this.mUserIsLogin = mUserIsLogin;
    }

    public String getmUserStatus() {
        return mUserStatus;
    }

    public void setmUserStatus(String mUserStatus) {
        this.mUserStatus = mUserStatus;
    }

}
