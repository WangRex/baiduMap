package com.baiduMap.pojo;

import com.baiduMap.common.BasePojo;

public class Marker extends BasePojo{
    private Long markerId;

    private String markerName;

    private String markerType;

    private String markerLnglat;

    private String markerAddress;

    public Long getMarkerId() {
        return markerId;
    }

    public void setMarkerId(Long markerId) {
        this.markerId = markerId;
    }

    public String getMarkerName() {
        return markerName;
    }

    public void setMarkerName(String markerName) {
        this.markerName = markerName == null ? "标注名称" : markerName.trim();
    }

    public String getMarkerType() {
        return markerType;
    }

    public void setMarkerType(String markerType) {
        this.markerType = markerType == null ? "标注类型" : markerType.trim();
    }

    public String getMarkerLnglat() {
        return markerLnglat;
    }

    public void setMarkerLnglat(String markerLnglat) {
        this.markerLnglat = markerLnglat == null ? "标注经纬度" : markerLnglat.trim();
    }

    public String getMarkerAddress() {
        return markerAddress;
    }

    public void setMarkerAddress(String markerAddress) {
        this.markerAddress = markerAddress == null ? "标注地址" : markerAddress.trim();
    }
}