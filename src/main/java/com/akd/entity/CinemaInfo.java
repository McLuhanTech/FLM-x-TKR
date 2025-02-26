package com.akd.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CinemaInfo {
    private Long id;

    private String cinemaCode;

    private String cinemaName;

    private String cinemaAddress;

    private String conactName;

    private String conactPhone;

    private String token;

    private Integer status;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

}