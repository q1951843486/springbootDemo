package com.meiya.authorization.entity;

import lombok.Data;

import java.util.Date;

@Data
public class AuthorizationInfo {

    private int id;


    private int authorizarId;


    private String authorizerPhoto;


    private Integer type;
    private String attorneysName;
    private String attorneysIdCard;
    private String attorneysPhone;
    private Integer vehicleInfoId;
    private String electronicSignature;
    private Date createTime;
    private Date updateTime;
    private String authorizationUrl;
    private String status;

    private String handleUserId;


    private Date handleTime;

    private String engineNum;


    private String frameNum;

}