package com.akd.entity;

import lombok.Data;

import java.util.Date;

@Data
public class KdmInfo {
    private Integer id;

    private String uuid;

    private String certificate;

    private String source;

    private String host;

    private String path;

    private Date createTime;

    private Date updateTime;


}