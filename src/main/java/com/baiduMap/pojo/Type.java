package com.baiduMap.pojo;

import com.baiduMap.common.BasePojo;

public class Type extends BasePojo {
	
    private Long typeId;

    private String typeName;

    private String typeLevel;
    
    private String typeParentId;

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getTypeLevel() {
        return typeLevel;
    }

    public void setTypeLevel(String typeLevel) {
        this.typeLevel = typeLevel == null ? null : typeLevel.trim();
    }

    public String getTypeParentId() {
        return typeParentId;
    }

    public void setTypeParentId(String typeParentId) {
        this.typeParentId = typeParentId == null ? null : typeParentId.trim();
    }
}