package com.baiduMap.common;

public class BasePojo {

    private String inputName;

    private String inputDate;

    private String updateName;

    private String updateDate;

    private String delsign;

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName == null ? "" : inputName.trim();
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate == null ? "" : inputDate.trim();
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName == null ? "" : updateName.trim();
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate == null ? "" : updateDate.trim();
    }

    public String getDelsign() {
        return delsign;
    }

    public void setDelsign(String delsign) {
        this.delsign = delsign == null ? "" : delsign.trim();
    }

}
