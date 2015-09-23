package com.baiduMap.pojo;

import com.baiduMap.common.BasePojo;

public class User extends BasePojo{
	
    private Long id;

    private String loginName;

    private String loginPassword;

    private String userName;

    private String role;

    private String roleName;

    private String status;

    private String statusName;

    private String token;

    private String markerFlag;

    private String markerFlagName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword == null ? null : loginPassword.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role =  role == null ? null : role.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName == null ? null : statusName.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getMarkerFlag() {
        return markerFlag;
    }

    public void setMarkerFlag(String markerFlag) {
        this.markerFlag = markerFlag == null ? null : markerFlag.trim();
    }

    public String getMarkerFlagName() {
        return markerFlagName;
    }

    public void setMarkerFlagName(String markerFlagName) {
        this.markerFlagName = markerFlagName == null ? null : markerFlagName.trim();
    }
}