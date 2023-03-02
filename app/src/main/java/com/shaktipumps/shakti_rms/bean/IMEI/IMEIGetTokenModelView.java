
package com.shaktipumps.shakti_rms.bean.IMEI;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class IMEIGetTokenModelView {

    @SerializedName("access_token")
    private String mAccessToken;
    @SerializedName("Dep_Pump_Targets")
    private String mDepPumpTargets;
    @SerializedName("Dep_SiteSurvey_Rights")
    private String mDepSiteSurveyRights;
    @SerializedName("Department")
    private String mDepartment;
    @SerializedName("DepartmentName")
    private String mDepartmentName;
    @SerializedName("DepartmentRole")
    private String mDepartmentRole;
    @SerializedName("District")
    private String mDistrict;
    @SerializedName(".expires")
    private String mExpires;
    @SerializedName("expires_in")
    private String mExpiresIn;
    @SerializedName(".issued")
    private String mIssued;
    @SerializedName("Organization")
    private String mOrganization;
    @SerializedName("OrganizationLogo")
    private String mOrganizationLogo;
    @SerializedName("OrganizationName")
    private String mOrganizationName;
    @SerializedName("refresh_token")
    private String mRefreshToken;
    @SerializedName("Role")
    private String mRole;
    @SerializedName("token_type")
    private String mTokenType;
    @SerializedName("UserId")
    private String mUserId;
    @SerializedName("UserName")
    private String mUserName;

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }

    public String getDepPumpTargets() {
        return mDepPumpTargets;
    }

    public void setDepPumpTargets(String depPumpTargets) {
        mDepPumpTargets = depPumpTargets;
    }

    public String getDepSiteSurveyRights() {
        return mDepSiteSurveyRights;
    }

    public void setDepSiteSurveyRights(String depSiteSurveyRights) {
        mDepSiteSurveyRights = depSiteSurveyRights;
    }

    public String getDepartment() {
        return mDepartment;
    }

    public void setDepartment(String department) {
        mDepartment = department;
    }

    public String getDepartmentName() {
        return mDepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        mDepartmentName = departmentName;
    }

    public String getDepartmentRole() {
        return mDepartmentRole;
    }

    public void setDepartmentRole(String departmentRole) {
        mDepartmentRole = departmentRole;
    }

    public String getDistrict() {
        return mDistrict;
    }

    public void setDistrict(String district) {
        mDistrict = district;
    }

    public String getExpires() {
        return mExpires;
    }

    public void setExpires(String expires) {
        mExpires = expires;
    }

    public String getExpiresIn() {
        return mExpiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        mExpiresIn = expiresIn;
    }

    public String getIssued() {
        return mIssued;
    }

    public void setIssued(String issued) {
        mIssued = issued;
    }

    public String getOrganization() {
        return mOrganization;
    }

    public void setOrganization(String organization) {
        mOrganization = organization;
    }

    public String getOrganizationLogo() {
        return mOrganizationLogo;
    }

    public void setOrganizationLogo(String organizationLogo) {
        mOrganizationLogo = organizationLogo;
    }

    public String getOrganizationName() {
        return mOrganizationName;
    }

    public void setOrganizationName(String organizationName) {
        mOrganizationName = organizationName;
    }

    public String getRefreshToken() {
        return mRefreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        mRefreshToken = refreshToken;
    }

    public String getRole() {
        return mRole;
    }

    public void setRole(String role) {
        mRole = role;
    }

    public String getTokenType() {
        return mTokenType;
    }

    public void setTokenType(String tokenType) {
        mTokenType = tokenType;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

}
