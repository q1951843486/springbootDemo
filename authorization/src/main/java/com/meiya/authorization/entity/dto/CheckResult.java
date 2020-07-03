package com.meiya.authorization.entity.dto;


import java.io.Serializable;

/**
 * @author gw
 * @project_name honest-parent
 * @create 2018-01-13 13:58
 **/
public class CheckResult implements Serializable {

    private String idCard;

    private String name;

    private String resultIdCard;

    private String resultName;

    private String photo;

    private String errorMesage;

    private String errorMesageCol;


    /**
     * 系统分析结果
     */
    private String resultFx;
    /**
     * 系统分析结果
     */
    private String resultXp;

    /**
     * 系统分析分数
     */
    private String resultFs;

    public CheckResult() {

    }

    @Override
    public String toString() {
        return "CheckResult{" +
                "idCard='" + idCard + '\'' +
                ", name='" + name + '\'' +
                ", resultIdCard='" + resultIdCard + '\'' +
                ", resultName='" + resultName + '\'' +
                ", photo='" + photo + '\'' +
                ", errorMesage='" + errorMesage + '\'' +
                ", errorMesageCol='" + errorMesageCol + '\'' +
                ", resultFx='" + resultFx + '\'' +
                ", resultXp='" + resultXp + '\'' +
                ", resultFs='" + resultFs + '\'' +
                '}';
    }

    public CheckResult(String idCard, String name, String resultIdCard, String resultName, String photo, String
            errorMesage, String errorMesageCol) {
        this.idCard = idCard;
        this.name = name;
        this.resultIdCard = resultIdCard;
        this.resultName = resultName;
        this.photo = photo;
        this.errorMesage = errorMesage;
        this.errorMesageCol = errorMesageCol;
    }

    public CheckResult(String errorMesage, String errorMesageCol) {
        this.errorMesage = errorMesage;
        this.errorMesageCol = errorMesageCol;
    }

    public CheckResult(String idCard, String name, String resultIdCard, String resultName, String photo, String errorMesage, String errorMesageCol, String resultFx, String resultFs) {
        this.idCard = idCard;
        this.name = name;
        this.resultIdCard = resultIdCard;
        this.resultName = resultName;
        this.photo = photo;
        this.errorMesage = errorMesage;
        this.errorMesageCol = errorMesageCol;
        this.resultFx = resultFx;
        this.resultFs = resultFs;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResultIdCard() {
        return resultIdCard;
    }

    public void setResultIdCard(String resultIdCard) {
        this.resultIdCard = resultIdCard;
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getErrorMesage() {
        return errorMesage;
    }

    public void setErrorMesage(String errorMesage) {
        this.errorMesage = errorMesage;
    }

    public String getErrorMesageCol() {
        return errorMesageCol;
    }

    public void setErrorMesageCol(String errorMesageCol) {
        this.errorMesageCol = errorMesageCol;
    }

    public String getResultFx() {
        return resultFx;
    }

    public void setResultFx(String resultFx) {
        this.resultFx = resultFx;
    }

    public String getResultXp() {
        return resultXp;
    }

    public void setResultXp(String resultXp) {
        this.resultXp = resultXp;
    }

    public String getResultFs() {
        return resultFs;
    }

    public void setResultFs(String resultFs) {
        this.resultFs = resultFs;
    }
}
