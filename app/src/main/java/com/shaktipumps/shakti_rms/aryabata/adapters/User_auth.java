package com.shaktipumps.shakti_rms.aryabata.adapters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class User_auth {

    @SerializedName("Id")
    @Expose
    private int id;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("MobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("EmailId")
    @Expose
    private String emailId;
    @SerializedName("MobileSerialNumber")
    @Expose
    private String mobileSerialNumber;
    @SerializedName("LastAuthenticatedDate")
    @Expose
    private String lastAuthenticatedDate;
    @SerializedName("UserStatus")
    @Expose
    private boolean userStatus;
    @SerializedName("SetParameters")
    @Expose
    private boolean setParameters;
    @SerializedName("PerformActions")
    @Expose
    private boolean performActions;
    @SerializedName("ViewParameters")
    @Expose
    private boolean viewParameters;

    @SerializedName("ViewLogs")
    @Expose
    private boolean viewLogs;

    public User_auth() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User_auth withId(int id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User_auth withUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User_auth withPassword(String password) {
        this.password = password;
        return this;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public User_auth withMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public User_auth withEmailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    public String getMobileSerialNumber() {
        return mobileSerialNumber;
    }

    public void setMobileSerialNumber(String mobileSerialNumber) {
        this.mobileSerialNumber = mobileSerialNumber;
    }

    public User_auth withMobileSerialNumber(String mobileSerialNumber) {
        this.mobileSerialNumber = mobileSerialNumber;
        return this;
    }

    public String getLastAuthenticatedDate() {
        return lastAuthenticatedDate;
    }

    public void setLastAuthenticatedDate(String lastAuthenticatedDate) {
        this.lastAuthenticatedDate = lastAuthenticatedDate;
    }

    public User_auth withLastAuthenticatedDate(String lastAuthenticatedDate) {
        this.lastAuthenticatedDate = lastAuthenticatedDate;
        return this;
    }

    public boolean isUserStatus() {
        return userStatus;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }

    public User_auth withUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
        return this;
    }

    public boolean isSetParameters() {
        return setParameters;
    }

    public void setSetParameters(boolean setParameters) {
        this.setParameters = setParameters;
    }

    public User_auth withSetParameters(boolean setParameters) {
        this.setParameters = setParameters;
        return this;
    }

    public boolean isPerformActions() {
        return performActions;
    }

    public void setPerformActions(boolean performActions) {
        this.performActions = performActions;
    }

    public User_auth withPerformActions(boolean performActions) {
        this.performActions = performActions;
        return this;
    }

    public boolean isViewParameters() {
        return viewParameters;
    }

    public void setViewParameters(boolean viewParameters) {
        this.viewParameters = viewParameters;
    }

    public User_auth withViewParameters(boolean viewParameters) {
        this.viewParameters = viewParameters;
        return this;
    }


    public boolean isViewLogs() {
        return viewLogs;
    }

    public void setViewLogs(boolean viewLogs) {
        this.viewLogs = viewLogs;
    }

    public User_auth withViewLogs(boolean viewLogs) {
        this.viewLogs= viewLogs;
        return this;
    }

}
