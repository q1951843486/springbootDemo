package com.meiya.authorization.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Authorizer {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authorizer.id
     *
     * @mbg.generated Fri Jul 03 16:44:02 CST 2020
     */
    private int id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authorizer.authorizer_name
     *
     * @mbg.generated Fri Jul 03 16:44:02 CST 2020
     */
    private String authorizerName;

    private String openId;
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authorizer.id_card
     *
     * @mbg.generated Fri Jul 03 16:44:02 CST 2020
     */
    private String idCard;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authorizer.phone
     *
     * @mbg.generated Fri Jul 03 16:44:02 CST 2020
     */
    private String phone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authorizer.photo_url
     *
     * @mbg.generated Fri Jul 03 16:44:02 CST 2020
     */
    private String photoUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authorizer.creation_time
     *
     * @mbg.generated Fri Jul 03 16:44:02 CST 2020
     */
    private Date creationTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authorizer.update_time
     *
     * @mbg.generated Fri Jul 03 16:44:02 CST 2020
     */
    private Date updateTime;

}